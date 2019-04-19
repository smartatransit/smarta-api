(ns smarta-api.schedule-client.core
  (:require [smarta-api.schedule-client.live.rail.core :as rail-live]))

(defn get-schedule [station]
  (rail-live/get-rail-schedule-by-station station))

(defn get-schedule-by-line [line]
  (rail-live/get-rail-schedule-by-line line))
