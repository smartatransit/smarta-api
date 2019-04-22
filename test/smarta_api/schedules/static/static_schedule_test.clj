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
  (let [arrivals (load-rail-schedule)]
    (testing "weekday arrivals"
      (testing "northbound/southbound"
        (testing "red line"
          (let [expected-red-station-count 19
                expected-red-arrivals 98
                expected-red-nb-first-station "Airport Station"
                expected-red-nb-last-station "North Springs Station"
                expected-red-sb-first-station "North Springs Station"
                expected-red-sb-last-station "Airport Station"
                weekday-nb-red-arrivals (get-in arrivals [:weekday :red :northbound])
                weekday-sb-red-arrivals (get-in arrivals [:weekday :red :southbound])]

            (is (= expected-red-station-count (count weekday-nb-red-arrivals)))
            (is (= expected-red-station-count (count weekday-sb-red-arrivals)))
            (is (= (count ((first weekday-nb-red-arrivals) :arrivals)) expected-red-arrivals))
            (is (= (count ((first weekday-sb-red-arrivals) :arrivals)) expected-red-arrivals))
            (is (= (count ((last weekday-nb-red-arrivals) :arrivals)) expected-red-arrivals))
            (is (= (count ((last weekday-sb-red-arrivals) :arrivals)) expected-red-arrivals))
            (is (= ((first weekday-nb-red-arrivals) :station-name) expected-red-nb-first-station))
            (is (= ((last weekday-nb-red-arrivals) :station-name) expected-red-nb-last-station))
            (is (= ((first weekday-sb-red-arrivals) :station-name) expected-red-sb-first-station))
            (is (= ((last weekday-sb-red-arrivals) :station-name) expected-red-sb-last-station))))
        (testing "gold line"
          (let [expected-gold-station-count 18
                expected-gold-arrivals 98
                expected-gold-nb-first-station "Airport Station"
                expected-gold-nb-last-station "Doraville Station"
                expected-gold-sb-first-station "Doraville Station"
                expected-gold-sb-last-station "Airport Station"
                weekday-nb-gold-arrivals (get-in arrivals [:weekday :gold :northbound])
                weekday-sb-gold-arrivals (get-in arrivals [:weekday :gold :southbound])]

            (is (= expected-gold-station-count (count weekday-nb-gold-arrivals)))
            (is (= expected-gold-station-count (count weekday-sb-gold-arrivals)))
            (is (= (count ((first weekday-nb-gold-arrivals) :arrivals)) expected-gold-arrivals))
            (is (= (count ((first weekday-sb-gold-arrivals) :arrivals)) expected-gold-arrivals))
            (is (= (count ((last weekday-nb-gold-arrivals) :arrivals)) expected-gold-arrivals))
            (is (= (count ((last weekday-sb-gold-arrivals) :arrivals)) expected-gold-arrivals))
            (is (= ((first weekday-nb-gold-arrivals) :station-name) expected-gold-nb-first-station))
            (is (= ((last weekday-nb-gold-arrivals) :station-name) expected-gold-nb-last-station))
            (is (= ((first weekday-sb-gold-arrivals) :station-name) expected-gold-sb-first-station))
            (is (= ((last weekday-sb-gold-arrivals) :station-name) expected-gold-sb-last-station)))))
      (testing "eastbound/westbound"
        (testing "green line"
          (let [expected-green-station-count 9
                expected-green-eb-arrivals 94
                expected-green-wb-arrivals 95
                expected-green-eb-first-station "Bankhead Station"
                expected-green-eb-last-station "Edgewood-Candler Park Station"
                expected-green-wb-first-station "Edgewood-Candler Park Station"
                expected-green-wb-last-station "Bankhead Station"
                weekday-eb-green-arrivals (get-in arrivals [:weekday :green :eastbound])
                weekday-wb-green-arrivals (get-in arrivals [:weekday :green :westbound])]

            (is (= expected-green-station-count (count weekday-eb-green-arrivals)))
            (is (= expected-green-station-count (count weekday-wb-green-arrivals)))
            (is (= (count ((first weekday-eb-green-arrivals) :arrivals)) expected-green-eb-arrivals))
            (is (= (count ((first weekday-wb-green-arrivals) :arrivals)) expected-green-wb-arrivals))
            (is (= (count ((last weekday-eb-green-arrivals) :arrivals)) expected-green-eb-arrivals))
            (is (= (count ((last weekday-wb-green-arrivals) :arrivals)) expected-green-wb-arrivals))
            (is (= ((first weekday-eb-green-arrivals) :station-name) expected-green-eb-first-station))
            (is (= ((last weekday-eb-green-arrivals) :station-name) expected-green-eb-last-station))
            (is (= ((first weekday-wb-green-arrivals) :station-name) expected-green-wb-first-station))
            (is (= ((last weekday-wb-green-arrivals) :station-name) expected-green-wb-last-station))))
        (testing "blue line"
          (let [expected-blue-station-count 15
                expected-blue-eb-arrivals 97
                expected-blue-wb-arrivals 98
                expected-blue-eb-first-station "Hamilton E. Holmes Station"
                expected-blue-eb-last-station "Indian Creek Station"
                expected-blue-wb-first-station "Indian Creek Station"
                expected-blue-wb-last-station "Hamilton E. Holmes Station"
                weekday-eb-blue-arrivals (get-in arrivals [:weekday :blue :eastbound])
                weekday-wb-blue-arrivals (get-in arrivals [:weekday :blue :westbound])]
            (is (= expected-blue-station-count (count weekday-eb-blue-arrivals)))
            (is (= expected-blue-station-count (count weekday-wb-blue-arrivals)))
            (is (= (count ((first weekday-eb-blue-arrivals) :arrivals)) expected-blue-eb-arrivals))
            (is (= (count ((first weekday-wb-blue-arrivals) :arrivals)) expected-blue-wb-arrivals))
            (is (= (count ((last weekday-eb-blue-arrivals) :arrivals)) expected-blue-eb-arrivals))
            (is (= (count ((last weekday-wb-blue-arrivals) :arrivals)) expected-blue-wb-arrivals))
            (is (= ((first weekday-eb-blue-arrivals) :station-name) expected-blue-eb-first-station))
            (is (= ((last weekday-eb-blue-arrivals) :station-name) expected-blue-eb-last-station))
            (is (= ((first weekday-wb-blue-arrivals) :station-name) expected-blue-wb-first-station))
            (is (= ((last weekday-wb-blue-arrivals) :station-name) expected-blue-wb-last-station))))))
    (testing "saturday arrivals"
      (testing "northbound/southbound"
        (testing "red line"
          (let [expected-red-station-count 19
                expected-red-nb-arrivals 97
                expected-red-sb-arrivals 58
                expected-red-nb-first-station "Airport Station"
                expected-red-nb-last-station "North Springs Station"
                expected-red-sb-first-station "North Springs Station"
                expected-red-sb-last-station "Airport Station"
                weekday-nb-red-arrivals (get-in arrivals [:saturday :red :northbound])
                weekday-sb-red-arrivals (get-in arrivals [:saturday :red :southbound])]

            (is (= expected-red-station-count (count weekday-nb-red-arrivals)))
            (is (= expected-red-station-count (count weekday-sb-red-arrivals)))
            (is (= (count ((first weekday-nb-red-arrivals) :arrivals)) expected-red-nb-arrivals))
            (is (= (count ((first weekday-sb-red-arrivals) :arrivals)) expected-red-sb-arrivals))
            (is (= (count ((last weekday-nb-red-arrivals) :arrivals)) expected-red-nb-arrivals))
            (is (= (count ((last weekday-sb-red-arrivals) :arrivals)) expected-red-sb-arrivals))
            (is (= ((first weekday-nb-red-arrivals) :station-name) expected-red-nb-first-station))
            (is (= ((last weekday-nb-red-arrivals) :station-name) expected-red-nb-last-station))
            (is (= ((first weekday-sb-red-arrivals) :station-name) expected-red-sb-first-station))
            (is (= ((last weekday-sb-red-arrivals) :station-name) expected-red-sb-last-station))))
        (testing "gold line"
          (let [expected-gold-station-count 18
                expected-gold-nb-arrivals 58
                expected-gold-sb-arrivals 59
                expected-gold-nb-first-station "Airport Station"
                expected-gold-nb-last-station "Doraville Station"
                expected-gold-sb-first-station "Doraville Station"
                expected-gold-sb-last-station "Airport Station"
                weekday-nb-gold-arrivals (get-in arrivals [:saturday :gold :northbound])
                weekday-sb-gold-arrivals (get-in arrivals [:saturday :gold :southbound])]

            (is (= expected-gold-station-count (count weekday-nb-gold-arrivals)))
            (is (= expected-gold-station-count (count weekday-sb-gold-arrivals)))
            (is (= (count ((first weekday-nb-gold-arrivals) :arrivals)) expected-gold-nb-arrivals))
            (is (= (count ((first weekday-sb-gold-arrivals) :arrivals)) expected-gold-sb-arrivals))
            (is (= (count ((last weekday-nb-gold-arrivals) :arrivals)) expected-gold-nb-arrivals))
            (is (= (count ((last weekday-sb-gold-arrivals) :arrivals)) expected-gold-sb-arrivals))
            (is (= ((first weekday-nb-gold-arrivals) :station-name) expected-gold-nb-first-station))
            (is (= ((last weekday-nb-gold-arrivals) :station-name) expected-gold-nb-last-station))
            (is (= ((first weekday-sb-gold-arrivals) :station-name) expected-gold-sb-first-station))
            (is (= ((last weekday-sb-gold-arrivals) :station-name) expected-gold-sb-last-station)))))
      (testing "eastbound/westbound"
        (testing "green line"
          (let [expected-green-station-count 9
                expected-green-eb-arrivals 58
                expected-green-wb-arrivals 59
                expected-green-eb-first-station "Bankhead Station"
                expected-green-eb-last-station "Edgewood-Candler Park Station"
                expected-green-wb-first-station "Edgewood-Candler Park Station"
                expected-green-wb-last-station "Bankhead Station"
                weekday-eb-green-arrivals (get-in arrivals [:saturday :green :eastbound])
                weekday-wb-green-arrivals (get-in arrivals [:saturday :green :westbound])]

            (is (= expected-green-station-count (count weekday-eb-green-arrivals)))
            (is (= expected-green-station-count (count weekday-wb-green-arrivals)))
            (is (= (count ((first weekday-eb-green-arrivals) :arrivals)) expected-green-eb-arrivals))
            (is (= (count ((first weekday-wb-green-arrivals) :arrivals)) expected-green-wb-arrivals))
            (is (= (count ((last weekday-eb-green-arrivals) :arrivals)) expected-green-eb-arrivals))
            (is (= (count ((last weekday-wb-green-arrivals) :arrivals)) expected-green-wb-arrivals))
            (is (= ((first weekday-eb-green-arrivals) :station-name) expected-green-eb-first-station))
            (is (= ((last weekday-eb-green-arrivals) :station-name) expected-green-eb-last-station))
            (is (= ((first weekday-wb-green-arrivals) :station-name) expected-green-wb-first-station))
            (is (= ((last weekday-wb-green-arrivals) :station-name) expected-green-wb-last-station))))
        (testing "blue line"
          (let [expected-blue-station-count 15
                expected-blue-eb-arrivals 60
                expected-blue-wb-arrivals 59
                expected-blue-eb-first-station "Hamilton E. Holmes Station"
                expected-blue-eb-last-station "Indian Creek Station"
                expected-blue-wb-first-station "Indian Creek Station"
                expected-blue-wb-last-station "Hamilton E. Holmes Station"
                weekday-eb-blue-arrivals (get-in arrivals [:saturday :blue :eastbound])
                weekday-wb-blue-arrivals (get-in arrivals [:saturday :blue :westbound])]
            (is (= expected-blue-station-count (count weekday-eb-blue-arrivals)))
            (is (= expected-blue-station-count (count weekday-wb-blue-arrivals)))
            (is (= (count ((first weekday-eb-blue-arrivals) :arrivals)) expected-blue-eb-arrivals))
            (is (= (count ((first weekday-wb-blue-arrivals) :arrivals)) expected-blue-wb-arrivals))
            (is (= (count ((last weekday-eb-blue-arrivals) :arrivals)) expected-blue-eb-arrivals))
            (is (= (count ((last weekday-wb-blue-arrivals) :arrivals)) expected-blue-wb-arrivals))
            (is (= ((first weekday-eb-blue-arrivals) :station-name) expected-blue-eb-first-station))
            (is (= ((last weekday-eb-blue-arrivals) :station-name) expected-blue-eb-last-station))
            (is (= ((first weekday-wb-blue-arrivals) :station-name) expected-blue-wb-first-station))
            (is (= ((last weekday-wb-blue-arrivals) :station-name) expected-blue-wb-last-station))))))

    (testing "sunday arrivals"
      (testing "northbound/southbound"
        (testing "red line"
          (let [expected-red-station-count 19
                expected-red-nb-arrivals 97
                expected-red-sb-arrivals 58
                expected-red-nb-first-station "Airport Station"
                expected-red-nb-last-station "North Springs Station"
                expected-red-sb-first-station "North Springs Station"
                expected-red-sb-last-station "Airport Station"
                weekday-nb-red-arrivals (get-in arrivals [:sunday :red :northbound])
                weekday-sb-red-arrivals (get-in arrivals [:sunday :red :southbound])]

            (is (= expected-red-station-count (count weekday-nb-red-arrivals)))
            (is (= expected-red-station-count (count weekday-sb-red-arrivals)))
            (is (= (count ((first weekday-nb-red-arrivals) :arrivals)) expected-red-nb-arrivals))
            (is (= (count ((first weekday-sb-red-arrivals) :arrivals)) expected-red-sb-arrivals))
            (is (= (count ((last weekday-nb-red-arrivals) :arrivals)) expected-red-nb-arrivals))
            (is (= (count ((last weekday-sb-red-arrivals) :arrivals)) expected-red-sb-arrivals))
            (is (= ((first weekday-nb-red-arrivals) :station-name) expected-red-nb-first-station))
            (is (= ((last weekday-nb-red-arrivals) :station-name) expected-red-nb-last-station))
            (is (= ((first weekday-sb-red-arrivals) :station-name) expected-red-sb-first-station))
            (is (= ((last weekday-sb-red-arrivals) :station-name) expected-red-sb-last-station))))
        (testing "gold line"
          (let [expected-gold-station-count 18
                expected-gold-nb-arrivals 58
                expected-gold-sb-arrivals 59
                expected-gold-nb-first-station "Airport Station"
                expected-gold-nb-last-station "Doraville Station"
                expected-gold-sb-first-station "Doraville Station"
                expected-gold-sb-last-station "Airport Station"
                weekday-nb-gold-arrivals (get-in arrivals [:sunday :gold :northbound])
                weekday-sb-gold-arrivals (get-in arrivals [:sunday :gold :southbound])]

            (is (= expected-gold-station-count (count weekday-nb-gold-arrivals)))
            (is (= expected-gold-station-count (count weekday-sb-gold-arrivals)))
            (is (= (count ((first weekday-nb-gold-arrivals) :arrivals)) expected-gold-nb-arrivals))
            (is (= (count ((first weekday-sb-gold-arrivals) :arrivals)) expected-gold-sb-arrivals))
            (is (= (count ((last weekday-nb-gold-arrivals) :arrivals)) expected-gold-nb-arrivals))
            (is (= (count ((last weekday-sb-gold-arrivals) :arrivals)) expected-gold-sb-arrivals))
            (is (= ((first weekday-nb-gold-arrivals) :station-name) expected-gold-nb-first-station))
            (is (= ((last weekday-nb-gold-arrivals) :station-name) expected-gold-nb-last-station))
            (is (= ((first weekday-sb-gold-arrivals) :station-name) expected-gold-sb-first-station))
            (is (= ((last weekday-sb-gold-arrivals) :station-name) expected-gold-sb-last-station)))))
      (testing "eastbound/westbound"
        (testing "green line"
          (let [expected-green-station-count 9
                expected-green-eb-arrivals 58
                expected-green-wb-arrivals 59
                expected-green-eb-first-station "Bankhead Station"
                expected-green-eb-last-station "Edgewood-Candler Park Station"
                expected-green-wb-first-station "Edgewood-Candler Park Station"
                expected-green-wb-last-station "Bankhead Station"
                weekday-eb-green-arrivals (get-in arrivals [:sunday :green :eastbound])
                weekday-wb-green-arrivals (get-in arrivals [:sunday :green :westbound])]

            (is (= expected-green-station-count (count weekday-eb-green-arrivals)))
            (is (= expected-green-station-count (count weekday-wb-green-arrivals)))
            (is (= (count ((first weekday-eb-green-arrivals) :arrivals)) expected-green-eb-arrivals))
            (is (= (count ((first weekday-wb-green-arrivals) :arrivals)) expected-green-wb-arrivals))
            (is (= (count ((last weekday-eb-green-arrivals) :arrivals)) expected-green-eb-arrivals))
            (is (= (count ((last weekday-wb-green-arrivals) :arrivals)) expected-green-wb-arrivals))
            (is (= ((first weekday-eb-green-arrivals) :station-name) expected-green-eb-first-station))
            (is (= ((last weekday-eb-green-arrivals) :station-name) expected-green-eb-last-station))
            (is (= ((first weekday-wb-green-arrivals) :station-name) expected-green-wb-first-station))
            (is (= ((last weekday-wb-green-arrivals) :station-name) expected-green-wb-last-station))))
        (testing "blue line"
          (let [expected-blue-station-count 15
                expected-blue-eb-arrivals 60
                expected-blue-wb-arrivals 59
                expected-blue-eb-first-station "Hamilton E. Holmes Station"
                expected-blue-eb-last-station "Indian Creek Station"
                expected-blue-wb-first-station "Indian Creek Station"
                expected-blue-wb-last-station "Hamilton E. Holmes Station"
                weekday-eb-blue-arrivals (get-in arrivals [:sunday :blue :eastbound])
                weekday-wb-blue-arrivals (get-in arrivals [:sunday :blue :westbound])]
            (is (= expected-blue-station-count (count weekday-eb-blue-arrivals)))
            (is (= expected-blue-station-count (count weekday-wb-blue-arrivals)))
            (is (= (count ((first weekday-eb-blue-arrivals) :arrivals)) expected-blue-eb-arrivals))
            (is (= (count ((first weekday-wb-blue-arrivals) :arrivals)) expected-blue-wb-arrivals))
            (is (= (count ((last weekday-eb-blue-arrivals) :arrivals)) expected-blue-eb-arrivals))
            (is (= (count ((last weekday-wb-blue-arrivals) :arrivals)) expected-blue-wb-arrivals))
            (is (= ((first weekday-eb-blue-arrivals) :station-name) expected-blue-eb-first-station))
            (is (= ((last weekday-eb-blue-arrivals) :station-name) expected-blue-eb-last-station))
            (is (= ((first weekday-wb-blue-arrivals) :station-name) expected-blue-wb-first-station))
            (is (= ((last weekday-wb-blue-arrivals) :station-name) expected-blue-wb-last-station))))))))
