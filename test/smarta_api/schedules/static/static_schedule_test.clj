(ns smarta-api.schedules.static.static-schedule-test
  (:require [smarta-api.schedules.static.static-schedule :refer :all])
  (:use clojure.test))

(def test-matrix [["a0" "b0" "c0"]
                  ["a1" "b1" "c1"]
                  ["a2" "b2" "c2"]
                  ["a3" "b3" "c3"]])

(deftest support-functions
  (testing "matrix rotation"
    (let [rotated-matrix (rotate-matrix test-matrix)]
     (is (= (get-in rotated-matrix [0 0]) "a0"))
     (is (= (get-in rotated-matrix [1 1]) "b1"))
     (is (= (get-in rotated-matrix [2 2]) "c2")))))

(deftest rail-directions-main-path
  (let [arrivals load-rail-schedule]
    (testing "weekday arrivals"
      (testing "northbound/southbound"
        (let [expected-red-station-count 19
              expected-gold-station-count 18
              weekday-nb-red-arrivals (get-in arrivals [:weekday :red :northbound])
              weekday-sb-red-arrivals (get-in arrivals [:weekday :red :southbound])
              weekday-nb-gold-arrivals (get-in arrivals [:weekday :gold :northbound])
              weekday-sb-gold-arrivals (get-in arrivals [:weekday :gold :southbound])]
          (is (= expected-red-station-count (count weekday-nb-red-arrivals)))
          (is (= expected-red-station-count (count weekday-sb-red-arrivals)))
          (is (= expected-gold-station-count (count weekday-nb-gold-arrivals)))
          (is (= expected-gold-station-count (count weekday-sb-gold-arrivals)))
          (is (= ((first weekday-nb-red-arrivals) :station-name) "Airport Station"))
          (is (= (count ((first weekday-nb-red-arrivals) :arrivals)) 97))
          (is (= ((last weekday-nb-red-arrivals) :station-name) "North Springs Station"))
          (is (= (count ((last weekday-nb-red-arrivals) :arrivals)) 97))
          (is (= ((first weekday-nb-gold-arrivals) :station-name) "Airport Station"))
          (is (= (count ((first weekday-nb-gold-arrivals) :arrivals)) 97))
          (is (= ((last weekday-nb-gold-arrivals) :station-name) "Doraville Station"))
          (is (= (count ((last weekday-nb-gold-arrivals) :arrivals)) 97))))
      (testing "eastbound/westbound"
       (let [ expected-blue-station-count 15
             expected-green-station-count 9
             weekday-eb-blue-arrivals (get-in arrivals [:weekday :blue :eastbound])
             weekday-wb-blue-arrivals (get-in arrivals [:weekday :blue :westbound])
             weekday-eb-green-arrivals (get-in arrivals [:weekday :green :eastbound])
             weekday-wb-green-arrivals (get-in arrivals [:weekday :green :westbound])])))))
