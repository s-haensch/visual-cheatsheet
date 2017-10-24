(ns hello-again.core
  (:require [reagent.core :as reagent :refer [atom]]
            [ajax.core :as ajx]
            [clojure.string :as strng] ))

  ; (enable-console-print!)


;; define your app data so that it doesn't get over-written on reload
(defonce app-state
  (atom { :functions []
        }))

;; load function descriptions from json-file
(ajx/GET "/functions.json"
   {:handler (fn [functions] (swap! app-state assoc :functions functions))
    :error-handler (fn [details] (.warn js/console (str "Failed to load functions from server: " details)))
    :response-format :json, :keywords? true})        

;; COMPONENTS

;; turns a "dot:purple" pair into the according element
(defn pair-to-element
  "maps a shape:color pair to a span with classes"
  [index pair]
  (let [[shape color]
        (strng/split pair "-")]
    ^{:key (str "item-" index)}
    [:span {:class (str "item " shape " " color)}]
  ))

;; A line with item descriptions like "[*dot:purple,rect:blue*])"
;; gets split into string pieces [ "[", "dot:purple,rect:blue", "]" ],
;; these pieces are processed here. Those pieces with item descriptions
;; get split further by a comma -> ["dot:purple", "rect:blue"] and are
;; then turned into a span element 
(defn convert-line-fragments
  "converts line fragments to items, if it contains shape:color pairs"

  [fragments]
  (map
    (fn [fragment]
      (if (re-find #"-" fragment)
        (map-indexed
          pair-to-element
          (strng/split fragment ","))
        fragment))
   fragments))


;; creates paragraphs from descriptions
(defn make-line [index line]
  ^{:key (str "line-" index)}
  [:p.line
    (if (re-find #"\*.+\*" line)
      (convert-line-fragments (strng/split line "*"))
      line)
  ])


;; card container    
(defn card [index props]
  ^{:key (str "card-" index)}
  [:div.card
    [:div.card-header
      [:h2.card-title (:title props)]
      [:p.description (:description props)]]

    (map-indexed
      (fn [index example]
        ^{:key (str "example-" index)}
        [:div.card-body
          [:div.function-wrapper
            [:div.function
              [:p 
                "(" [:span.function-name (:title props)]]
              (map-indexed
                make-line (:lines example))]]
          [:div.output
            ; [:p.arrow "=>  "]
            (map-indexed
              make-line (:output example))]])
      (:examples props))
    
     
    [:div.card-footer
      [:a.link {
          :href (:examples-link props)
          :target "blank"
        } "» Examples"]
      [:a.link {
          :href (:source-link props)
          :target "blank"
        } "» Source"]]
    ])
        


;; main
(def divide-by-three 
  (partition-all
    (.ceil js/Math (/
      (count (:functions @app-state)) 3))
   (:functions @app-state)))

(defn app []
  [:div.content
    [:h1.headline "The " [:span.emph "ClojureScript"] " Visual Cheatsheet"]
    
    [:div.col
      (map-indexed card (nth divide-by-three 0)) ]
    [:div.col
      (map-indexed card (nth divide-by-three 1)) ]
    [:div.col
      (map-indexed card (nth divide-by-three 2)) ]
  ])

;; render
(reagent/render-component [ app ]
  (. js/document (getElementById "app")))
