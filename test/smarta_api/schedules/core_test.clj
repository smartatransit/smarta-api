(ns smarta-api.schedules.core-test
  (:require [smarta-api.schedules.core :refer :all])
  (:use clojure.test))

(defn get-rail-schedule-by-station-mock [station]
  [{:station "Test Station"}])

(defn get-rail-schedule-by-line-mock [line]
  [{:station "Test Station"}])

(deftest rail-operations
  (with-redefs [smarta-api.schedules.live.rail.core/get-rail-schedule-by-station get-rail-schedule-by-station-mock
                smarta-api.schedules.live.rail.core/get-rail-schedule-by-line get-rail-schedule-by-line-mock]
    (testing "schedule by station"
      (let [expected-keys [:arrivals]
            schedule (get-schedule "Test Station")]
        (is (= expected-keys (keys schedule)))
        (is (not (empty? (schedule :arrivals))))))
    (testing "schedule by line"
      (let [expected-keys [:arrivals]
            schedule (get-schedule-by-line "RED")]
        (is (= expected-keys (keys schedule)))
        (is (not (empty? (schedule :arrivals))))))))
