(ns hello-again.core
  (:require [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

;; define your app data so that it doesn't get over-written on reload
(defonce app-state (atom
  { :text "Hello!" }))

;; COMPONENTS
;; dot
(defn dot [color]
  [:span {:class (str "item dot " color)}])

;; rect
(defn rect [color]
  [:span {:class (str "item rect " color)}] )

;; array of dots
(defn item-array
  ([items]
    (for [item items]
      ((get item :shape) (get item :color))))
  ([item times]
    (for [num (range times)]
      ((get item :shape) (get item :color)))))

;; card container
(defn card [props]
  [:div.card
    [:div.card-header
      [:table
        [:thead [:tr [:th (get props :title)]]]
        [:tbody [:tr
          [:td.package (get props :package)]
          [:td.since
            "since 1.0 ( "
            [:a {
                :href (get props :source-link)
                :target "blank"
              } "source"]
            " )"]]]]]

    [:div.card-body
      [:div.function
        [:p 
          "(" [:span.function-name (get props :title)]]
        (for [line (get props :lines)]
          [:p.inset-1 (cons "" line)])]
      [:div.output
        [:p (cons "=>  " (get props :output))]]]

    [:div.card-footer
      [:a {
          :href (get props :examples-link)
          :target "blank"
        } "» Examples"]]])

;; props "map"
(def properties-map {
  :title "map"
  :package "clojure.core"
  :since "1.0"
  :source-link "https://github.com/clojure/clojure/blob/clojure-1.9.0-alpha14/src/clj/clojure/core.clj#L2700"
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
  })

;; props "filter"
(def properties-filter {
  :title "filter"
  :package "clojure.core"
  :since "1.0"
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

;; main
(defn app []
  [:div.content
    [:h1.headline "The Visual Cheatsheet — " [:span.emph "ClojureScript"]]
    (card properties-map)
    (card properties-filter)
  ])

;; render
(reagent/render-component [ app ]
  (. js/document (getElementById "app")))
