(ns smarta-api.schedules.common.util
  (:require [clojure.string :as string]
            [camel-snake-kebab.core :refer :all]))

(defn rotate-matrix
  "Rotate a matrix counter-clockwise"
  [matrix]
  (apply mapv vector matrix))

(defn capitalize-words
  "Capitalize every word in a string"
  [s]
  (->> (string/split (str s) #"\b")
       (map string/capitalize)
       string/join))

(defn to-keyword [key]
  (keyword (->kebab-case (.toLowerCase key))))
