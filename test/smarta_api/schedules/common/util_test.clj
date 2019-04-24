(ns smarta-api.schedules.common.util-test
  (:require [smarta-api.schedules.common.util :refer :all])
  (:use clojure.test))

(deftest utility-operations
  (testing "capitalization"
    (let [expected-string "Test Testing Tested"
          capitalized-word (capitalize-words "test testIng TESTed")]
      (is (= expected-string capitalized-word))))
  (testing "kebab keywords"
    (let [expected-keyword :test-testing-tested
          test-keyword (to-keyword "test testIng TESTed")]
      (is (= expected-keyword test-keyword)))))
