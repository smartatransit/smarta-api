(ns smarta-api.schedules.common.distance-test
  (:require [smarta-api.schedules.common.distance :refer :all])
  (:use clojure.test))

(def test-start-location {:latitude  33.755886 :longitude -84.387801})

(deftest station-operations
  (testing "load stations"
    (let [stations (load-stations)
          station-count (count stations)
          expected-station-count 37]
      (is (= station-count expected-station-count))))
  (testing "get station sorted by distance"
    (let [{start-lat :latitude
           start-long :longitude} test-start-location
          stations (get-stations-by-distance start-lat start-long)
          station-count (count stations)
          station (first stations)
          expected-station-count 37
          expected-station-name "Peachtree Center Station"
          expected-station-distance 0.2522641723929518]
      (is (= expected-station-count station-count))
      (is (= expected-station-name (:station-name station)))
      (is (= expected-station-distance (:distance station))))))
