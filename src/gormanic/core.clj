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

(defn gregorian-to-parts [gregorian-date]
  {:day  (.get (.dayOfYear gregorian-date))
   :year (.get (.year gregorian-date))})

(defn gormanic-to-parts [gormanic]
  {:day
         (if (:intermission gormanic)
           365
           (+ (* (- (:month gormanic) 1) 28) (:day gormanic)))
   :year (:year gormanic)})

(defn parts-to-gormanic [parts]
  (let [day (:day parts)]
    (if (>= day 365)
      {:year (:year parts) :intermission true}
      {:year  (:year parts)
       :month (gormanic-month-index day)
       :day   (gormanic-day-index day)})))

(defn gormanic-to-string [gormanic]
  (if (:intermission gormanic)
    (str "Intermission " (:year gormanic))
    (str (:day gormanic) " "
         (gormanic-months (- (:month gormanic) 1)) " " (:year gormanic))))

(defn gregorian-to-gormanic
  [gregorian]
  (parts-to-gormanic
    (gregorian-to-parts gregorian)))

(defn gregorian-to-gormanic-string
  [gregorian]
  (gormanic-to-string (gregorian-to-gormanic gregorian)))

(defn parts-to-gregorian [parts]
  (.plusDays (t/date-time (:year parts) 1 1) (- (:day parts) 1)))

(defn gormanic-to-gregorian
  [gormanic]
  (parts-to-gregorian (gormanic-to-parts gormanic)))
