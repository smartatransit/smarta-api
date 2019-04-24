(ns smarta-api.schedules.live.rail.core
  (:require [cheshire.core :refer :all]
            [clj-http.client :as client]
            [clojure.string :as string]
            [smarta-api.schedules.common.api :as api]
            [smarta-api.schedules.common.util :refer :all]
            [camel-snake-kebab.core :refer :all]))

(defn get-rail-schedule []
  (let [arrivals (client/get (api/get-rail-arrivals-endpoint))]
    (parse-string (:body arrivals) to-keyword)))

(defn get-rail-details []
  (reduce
   (fn [acc item]
     (conj acc {:line (:line item)
                :line-friendly (capitalize-words (:line item))
                :station (:station item)
                :station-friendly (capitalize-words (:station item))})) [] (get-rail-schedule)))

(defn get-rail-lines [] (distinct (map #(:line %) (get-rail-details))))

(defn get-rail-stations [] (distinct (map #(:station %) (get-rail-details))))

(defn get-rail-schedule-by-station [station]
  (filter #(= (:station %) station) (get-rail-schedule)))

(defn get-rail-schedule-by-line [line]
  (filter #(= (:line %) line) (get-rail-schedule)))
