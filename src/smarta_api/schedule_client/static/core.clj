(ns smarta-api.schedule-client.static.core
  (:require [cheshire.core :refer :all]
            [clojure.string :as string]
            [smarta-api.schedule-client.common.util :refer :all]
            [smarta-api.schedule-client.static-schedule :as stan]
            [camel-snake-kebab.core :refer :all]
            [clojure.data.csv :as csv]
            [clojure.java.io :as io]))

(def static-rail-schedule (stan/load-rail-schedule))
