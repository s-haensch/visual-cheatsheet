(ns hello-again.core
    (:require [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)


;; define your app data so that it doesn't get over-written on reload
(defonce app-state (atom
  {
    :text "Hello there!"
    :name "Steffen"
  }))



;; dot
(defn dot
  [color]
  [:span {:class (str "bubble " color)}])


;; array of dots
(defn item-array
  ([colors]
  (for [color colors]
    (dot color)))
  
  ([color times]
    (for [num (range times)]
      (dot color)))
  )


;; card container
(defn card
  [props]

  [:div.card
    [:div.function
      [:p 
        "(" [:span.function-name "map"]]

      (for [line (get props :lines)]
        [:p.inset-1 (cons "" line)])
    ]

    [:div.output
      [:p (cons "=> " (get props :output))]
    ]
  ]
)


(def properties {
  :title "map"
  
  :lines [
    "make-blue"

    [
      "["
      (item-array ["green" "purple" "blue" "yellow" "red"])
      "])"
    ]
  ]
  
  :output [
    "(" (item-array "blue" 5) ")"
  ]}
)

(reagent/render-component [card properties]
  (. js/document (getElementById "app")))
