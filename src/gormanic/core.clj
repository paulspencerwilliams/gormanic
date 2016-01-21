(ns gormanic.core)

(def months ["March",
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

(defn month-index [day-of-year] (quot (- day-of-year 1) 28))

(defn day-of-month [day-of-year] (+ (rem (- day-of-year 1) 28) 1))

(defn year [gregorian-date] (.get (.year gregorian-date)))

(defn day-of-year [gregorian-date] (.get (.dayOfYear gregorian-date)))

(defn convert
  [gregorian-date]
  (let [day (day-of-year gregorian-date)
        month (month-index day)]
    (if (< month 13)
      (str (day-of-month day) " " (months month) " " (year gregorian-date))
      "Intermission")))