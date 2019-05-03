(ns smarta-api.schedules.common.util-test
  (:require [smarta-api.schedules.common.util :refer :all])
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

(deftest utility-operations
  (testing "capitalization"
    (let [expected-string "Test Testing Tested"
          capitalized-word (capitalize-words "test testIng TESTed")]
      (is (= expected-string capitalized-word))))
  (testing "kebab keywords"
    (let [expected-keyword :test-testing-tested
          test-keyword (to-keyword "test testIng TESTed")]
      (is (= expected-keyword test-keyword)))))
