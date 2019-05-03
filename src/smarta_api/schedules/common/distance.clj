(ns smarta-api.schedules.common.distance
  (:require [geo.spatial :as spatial]
            [geo.geohash :as ghash]
            [smarta-api.schedules.common.util :refer :all]
            [clojure.data.csv :as csv]
            [clojure.java.io :as io]))

(def station-file-location "data/location/stations.csv")

(def meter-to-foot 3.28084)

(def mile 5280)

(defn load-stations []
  (with-open [reader (io/reader station-file-location)]
    (rotate-matrix
     (doall
      (csv/read-csv reader)))))

(defn get-stations []
  (reduce
   (fn [acc item]
     (conj acc {:station-name (get item 0)
                :location (get item 1)})) [] (load-stations)))

(defn lat-long-to-geohash [lat long])

(defn geohash-to-lat-long [geohash]
  (let [center (ghash/geohash-center (ghash/geohash geohash))]
    [(.getLatitude center) (.getLongitude center)]))

(defn get-crow-distance
  "Gets distance (in km)  between two points as the crow flies"
  [departure destination]
  (/ (spatial/distance departure destination) 1000))

(defn get-stations-by-distance [lat long]
  (let [stations (get-stations)
        current-address (spatial/spatial4j-point lat long)]
    (sort-by :distance
             (reduce
              (fn [acc item]
                (let [[station-lat station-long] (geohash-to-lat-long (:location item))
                      station-point (spatial/spatial4j-point station-lat station-long)
                      distance (get-crow-distance current-address station-point)]
                  (conj acc (assoc item :distance distance)))) [] stations))))
