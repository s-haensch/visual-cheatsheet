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
    [:div.function
      [:p 
        "(" [:span.function-name (get props :title)]]
      (for [line (get props :lines)]
        [:p.inset-1 (cons "" line)])]

    [:div.output
      [:p (cons "=> " (get props :output))]]])


;; props "map"
(def properties-map {
  :title "map"
  :lines [
    "make-blue"
    [ "["
      (item-array [
        {:color "green"  :shape dot}
        {:color "purple" :shape rect}
        {:color "blue"   :shape dot}
        {:color "yellow" :shape dot}
        {:color "red"    :shape rect}])
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
    ")"
  ]})

;; props "filter"
(def properties-filter {
  :title "filter"
  :lines [
    "red?"
    [ "["
      (item-array [
        {:color "green"  :shape dot}
        {:color "red"    :shape rect}
        {:color "blue"   :shape rect}
        {:color "yellow" :shape dot}
        {:color "red"    :shape dot}])
      "])" ] ]
  :output [
    "("
    (item-array [
        {:color "red" :shape rect}
        {:color "red" :shape dot}])
    ")" ]})


;; main
(defn app []
  [:div
    (card properties-map)
    (card properties-filter)
  ])

;; render
(reagent/render-component [ app ]
  (. js/document (getElementById "app")))
