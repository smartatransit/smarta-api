(ns smarta-api.schedules.live.rail.core
  (:require [cheshire.core :refer :all]
            [clj-http.client :as client]
            [clojure.string :as string]
            [smarta-api.schemas.rail :as rs]
            [smarta-api.schedules.common.api :as api]
            [smarta-api.schedules.common.util :refer :all]
            [camel-snake-kebab.core :refer :all]))

(defn get-rail-schedule []
  (let [arrivals (client/get (api/get-rail-arrivals-endpoint))]
    (parse-string (:body arrivals) to-keyword)))

(defn to-schema-result [schedule-item]
  {:station  {:name      (capitalize-words (:station schedule-item))
              :line      (capitalize-words (:line schedule-item))
              :direction (capitalize-words (:direction schedule-item))}
   :schedule {:train-id        (:train-id schedule-item)
              :next-station    (capitalize-words (:station schedule-item))
              :destination     (str (capitalize-words (:destination schedule-item) ) " Station")
              :next-arrival    (:next-arr schedule-item)
              :waiting-seconds (:waiting-seconds schedule-item)
              :waiting-time    (:waiting-time schedule-item)
              :event-time      (:event-time schedule-item)}})

(defn get-schema-schedule []
  (reduce
    (fn [acc item]
        (conj acc (to-schema-result item))) [] (get-rail-schedule)))

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
