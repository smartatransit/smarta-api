(ns smarta-api.schedules.static.core-test
  (:require [smarta-api.schedules.static.core :refer :all])
  (:use clojure.test))

(deftest scheduling-happy-path
  (testing "lines"
    (let [lines (get-lines)
          expected-line-count 4]
      (is (= expected-line-count (count lines)))))
  (testing "schedules"
    (let [schedules (get-schedules)
          expected-schedule-count 3]
      (is (= expected-schedule-count (count schedules))))))
