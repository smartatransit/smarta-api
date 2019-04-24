(ns smarta-api.schedules.static.core-test
  (:require [smarta-api.schedules.static.core :refer :all])
  (:use clojure.test))

(deftest scheduling-operations
  (testing "lines"
    (let [lines (get-lines)
          expected-line-count 4]
      (is (= expected-line-count (count lines)))))
  (testing "schedules"
    (let [schedules (get-schedules)
          expected-schedule-count 3]
      (is (= expected-schedule-count (count schedules)))))
  (testing "directions"
    (let [directions (get-directions)
          expected-direction-count 4]
      (is (= expected-direction-count (count directions)))))
  (testing "stations"
    (let [expected-station-count 19
          stations (get-stations :weekday :red :northbound)]
      (is (= expected-station-count (count stations))))))

(deftest station-operations
  (testing "get valid statio"
    (let [expected-station-name "Airport Station"
          expected-arrivals 98
          station (get-station expected-station-name :weekday :red :northbound)]
      (is (not (nil? station)))
      (is (= expected-station-name (station :station-name)))
      (is (= expected-arrivals (count (station :arrivals))))))
  (testing "get missing station"
    (let [station (get-station "Fake Station" :weekday :red :northbound)]
      (is (nil? station))))
  (testing "valid station schedule composition"
    (let [directions ["northbound" "southbound"]
          expected-directions (map keyword directions)
          schedule (compose-station-schedule "Airport Station" :weekday :red directions)]
      (is (= expected-directions (keys schedule)))))
  (testing "invalid station schedule composition"
    (let [directions ["northbound" "southbound"]
          schedule (compose-station-schedule "Fake Station" :weekday :red directions)]
      (is (empty? schedule))))
  (testing "get valid station schedule"
    (let [expected-station-name "Airport Station"
          expected-keys [:station-name :red :gold]
          station-schedule (get-schedule-by-station :weekday expected-station-name)]
      (is (= expected-station-name (station-schedule :station-name)))
      (is (= expected-keys (keys station-schedule)))))
  (testing "get invalid station schedule"
    (let [expected-keys [:station-name]
          station-schedule (get-schedule-by-station :weekday "Fake Station")]
      (is (= expected-keys (keys station-schedule))))))
