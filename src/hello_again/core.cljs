(ns hello-again.core
  (:require [reagent.core :as reagent :refer [atom]]))

  ; (enable-console-print!)

;; define your app data so that it doesn't get over-written on reload
(defonce app-state (atom
  { :text "Hello!" }))

;; COMPONENTS
;; dot
(defn dot [props]
  ^{:key (str "item-" (nth props 1))}
  [:span {:class (str "item dot " (nth props 0))}])

;; rect
(defn rect [props]
  ^{:key (str "item-" (nth props 1))}
  [:span {:class (str "item rect " (nth props 0))}] )

;; array of dots
(defn item-array
  ([items]
    (map-indexed #((get %2 :shape) [(get %2 :color) %1]) ;; %1 is index, %2 is array object
        items)))

;; card container
(defn card [props]
  [:div {:class (str "card " (:size props))}
    [:div.card-header
      [:h2.card-title (:title props)]
      [:p.description (:p.description props)]
      ]

    [:div.card-body
      [:div.function-wrapper
        [:div.function
          [:p 
            "(" [:span.function-name (:title props)]]
          (map-indexed
            (fn [index, line] ^{:key (str "line-" index)}
              [:p.inset-1 (cons "" line)]) (:lines props))]]

      [:div.output
        [:p (cons "=>  " (:output props))]]]

    [:div.card-footer
      [:a.link {
          :href (:examples-link props)
          :target "blank"
        } "» Examples"]
      [:a.link {
          :href (:source-link props)
          :target "blank"
        } "» Source"]]])

;; card properties        
(def properties-map {
  :title "map"
  :package "clojure.core"
  :since "1.0"
  :p.description "Applies a function to every item in a collection and returns
        a new list of resulting items."
  :lines [
    "make-blue"
    [ "["
      (item-array [
        {:color "green"  :shape dot}
        {:color "green"  :shape rect}
        {:color "purple" :shape dot}
        {:color "green"  :shape dot}
        {:color "purple" :shape rect}])
      "])"
    ]]
  :output [
    "("
    (item-array [
        {:color "blue"  :shape dot}
        {:color "blue"  :shape rect}
        {:color "blue"  :shape dot}
        {:color "blue"  :shape dot}
        {:color "blue"  :shape rect}])
    ")"]
  :examples-link "http://clojuredocs.org/clojure.core/map"
  :source-link "https://github.com/clojure/clojure/blob/clojure-1.9.0-alpha14/src/clj/clojure/core.clj#L2700"
  })

(def properties-filter {
  :title "filter"
  :package "clojure.core"
  :since "1.0"
  :p.description "Applies a boolean function to all items in a
    collection and return a list of all items returning TRUE."
  :source-link "https://github.com/clojure/clojure/blob/clojure-1.9.0-alpha14/src/clj/clojure/core.clj#L2766"
  :lines [
    "purple?"
    [ "["
      (item-array [
        {:color "purple"  :shape dot}
        {:color "blue"    :shape rect}
        {:color "blue"    :shape dot}
        {:color "green"   :shape dot}
        {:color "purple"  :shape rect}])
      "])" ] ]
  :output [
    "("
    (item-array [
        {:color "purple"  :shape dot}
        {:color "purple"  :shape rect}])
    ")" ]
  :examples-link "http://clojuredocs.org/clojure.core/filter"
  })

(def properties-for {
  :title "for"
  :package "clojure.core"
  :since "1.0"
  :p.description ""
  :source-link "https://github.com/clojure/clojure/blob/clojure-1.9.0-alpha14/src/clj/clojure/core.clj#L4576"
  :lines [
    [ "[x ["
      (item-array [
        {:color "green"  :shape dot}
        ]) "] y ["
      (item-array [
        {:color "blue"  :shape dot}
        {:color "purple"  :shape rect}
        ])
      "]]"
    ]
    "[x y])"
    ]
  :output [
    "(["
    (item-array [
        {:color "green"  :shape dot}
        {:color "blue"  :shape dot}])
    "] ["
    (item-array [
        {:color "green"  :shape dot}
        {:color "purple"  :shape rect}])
    "])"
  ]
  :examples-link "http://clojuredocs.org/clojure.core/for"
  })

(def properties-assoc {
  :title "assoc"
  :package "clojure.core"
  :since "1.0"
  :source-link "https://github.com/clojure/clojure/blob/clojure-1.9.0-alpha14/src/clj/clojure/core.clj#L181"
  :lines [
    [ "["
      (item-array [
        {:color "green"  :shape dot}
        {:color "green"  :shape dot}
        {:color "green"  :shape dot}
        ]) "]"]
      ["1 " (rect ["purple" 0])
      ")"]]
  :output [
    "["
    (item-array [
        {:color "green"  :shape dot}
        {:color "purple" :shape rect}
        {:color "green"  :shape dot}])
    "]"
  ]
  :examples-link "http://clojuredocs.org/clojure.core/assoc"
  })

(def properties-assoc-in {
  :title "assoc-in"
  :package "clojure.core"
  :since "1.0"
  :source-link "https://github.com/clojure/clojure/blob/clojure-1.9.0-alpha14/src/clj/clojure/core.clj#L6043"
  :lines [
    [ "[[" (item-array [
        {:color "blue"  :shape dot}
        {:color "blue"  :shape dot} ]) "]"
      "[" (item-array [
        {:color "purple"  :shape rect}
        {:color "purple"  :shape rect} ]) "]]"]
    [ "[1 0] " (dot ["green" 0]) ")" ]]
  :output [
    "[[" (item-array [
      {:color "blue"  :shape dot}
      {:color "blue"  :shape dot}]) "] ["
    (item-array [
      {:color "green"  :shape dot}
      {:color "purple"  :shape rect}]) "]]"
  ]
  :examples-link "http://clojuredocs.org/clojure.core/assoc-in"
  })

;; main
(defn app []
  [:div.content
    [:h1.headline "The Visual Cheatsheet — " [:span.emph "ClojureScript"]]
    [:div.col
      (card properties-map)
      (card properties-assoc)
    ]
    [:div.col
      (card properties-filter)
      (card properties-assoc-in)
    ]
    [:div.col
      (card properties-for)
    ]
  ])

;; render
(reagent/render-component [ app ]
  (. js/document (getElementById "app")))
