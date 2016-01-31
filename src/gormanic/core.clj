(ns gormanic.core
  (:require [clj-time.core :as t]))

(def gormanic-months ["March",
                      "April",
                      "May",
                      "June",
                      "Quintilis",
                      "Sextilis",
                      "September",
                      "October",
                      "November",
                      "December",
                      "January",
                      "February",
                      "Gormanuary"])

(defn gormanic-day-index [day-of-year] (+ (rem (- day-of-year 1) 28) 1))

(defn gormanic-month-index [day-of-year] (+ (quot (- day-of-year 1) 28) 1))

(defn gregorian-to-gormanic
  [gregorian]
  (let [day  (.get (.dayOfYear gregorian))
        year (.get (.year gregorian))]
    (if (>= day 365)
      {:year year :intermission true}
      {:year  year
       :month (gormanic-month-index day)
       :day   (gormanic-day-index day)})))

(defn gormanic-to-gregorian [gormanic]
  (let [day (if (:intermission gormanic)
              365
              (+ (* (- (:month gormanic) 1) 28) (:day gormanic)))
        year (:year gormanic)]
  (.plusDays (t/date-time year 1 1) (- day 1))))

(defn gormanic-to-string [gormanic]
  (if (:intermission gormanic)
    (str "Intermission " (:year gormanic))
    (str (:day gormanic) " "
         (gormanic-months (- (:month gormanic) 1)) " " (:year gormanic))))

(defn gregorian-to-gormanic-string
  [gregorian]
  (gormanic-to-string (gregorian-to-gormanic gregorian)))

