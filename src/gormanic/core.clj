(ns gormanic.core)

(def months [ "March", "April", "May",
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

(defn convert
  [gregorian-date]
  (let [day-of-year (.get (.dayOfYear gregorian-date))
        year (.get (.year gregorian-date))
        month-index (- (rem day-of-year 28) 1)
        day-index (+ (quot day-of-year 28) 1) ]
      (str day-index " " (months month-index) " " year)))