(ns smarta-api.schedules.common.config-test
  (:require [smarta-api.schedules.common.config :refer :all])
  (:use clojure.test))

(deftest marta-config-values
  (testing "api values"
    (let [expected-keys [:marta-api-key :marta-api-uri]]
      (= expected-keys (keys config)))))
