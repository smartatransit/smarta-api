(ns smarta-api.schedules.live.rail.core-test
  (:require [smarta-api.schedules.live.rail.core :refer :all]
            [cheshire.core :refer :all]
            [clj-http.client :as c])
  (:use [clojure.test]
        [clj-http.fake]))

(def rail-arrivals-endpoint "http://developer.itsmarta.com/RealtimeTrain/RestServiceNextTrain/GetRealtimeArrivals?apiKey=some-api-key")

(def rail-arrivals-response-body
  '({:destination "Station 2"
     :direction "W"
     :event_time "4/24/2019 8:34:12 PM",
     :line "BLUE",
     :next_arr "08:34:21 PM",
     :station "Station 1",
     :train_id "104206",
     :waiting_seconds "-39",
     :waiting_time "Boarding"}
    {:destination "Station 3"
     :direction "W"
     :event_time "4/24/2019 8:34:12 PM",
     :line "BLUE",
     :next_arr "08:34:21 PM",
     :station "Station 2",
     :train_id "104206",
     :waiting_seconds "-39",
     :waiting_time "Boarding"}))

(defn get-rail-arrivals-mock [request]
  {:status 200 :headers {} :body (generate-string rail-arrivals-response-body)})

(deftest rail-operations
  (with-fake-routes {rail-arrivals-endpoint get-rail-arrivals-mock}
    (testing "lines"
      (let [expected-line-count 1
            expected-lines ["Blue"]
            rail-lines (get-rail-lines)]
        (is (= expected-line-count (count rail-lines)))
        (is (= expected-lines rail-lines))))
    (testing "stations"
      (let [expected-station-count 2
            expected-stations ["Station 1" "Station 2"]
            rail-stations (get-rail-stations)]
        (is (= expected-station-count (count rail-stations)))
        (is (= expected-stations rail-stations))))
    (testing "schedule"
      (testing "by station"
        (let [expected-schedule-count 1
              expected-station-name "Station 1"
              schedule (get-rail-schedule-by-station "Station 1")]
          (is (= expected-schedule-count (count schedule)))
          (is (= expected-station-name (get-in (first schedule) [:station :name])))))
      (testing "by line"
        (let [expected-schedule-count 2
              expected-station-name "Station 1"
              schedule (get-rail-schedule-by-line "Blue")]
          (is (= expected-schedule-count (count schedule)))
          (is (= expected-station-name (get-in (first schedule) [:station :name]))))))))
