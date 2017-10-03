(ns hello-again.core
    (:require [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)


;; define your app data so that it doesn't get over-written on reload
(defonce app-state (atom
  {
    :text "Hello there!"
    :name "Steffen"
  }))


(def card-description {
  :title "map"
  })

;; dot
(defn dot
  [color]
  [:span {:class (str "bubble " color)}])


;; array of dots
(defn dot-array
  [colors]
  (for [color colors]
    (dot color)))


;; card container
(defn card []
  [:div.card
    [:div.function
      [:p 
        "(" [:span.function-name (get card-description :title)]]
      [:p.inset-1 "make-blue"]
      [:p.inset-1
        "["
        (dot-array ["green" "purple" "blue" "yellow" "red"])
        "])" ]]

    [:div.output
      [:p
        "=> ("
        (dot-array ["blue" "blue"])
        ")"]]
  ])


(reagent/render-component [card]
  (. js/document (getElementById "app")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  (swap! app-state update-in [:__figwheel_counter] inc)
)
