(ns gormanic.core)

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

(defn gormanic-month-index [day-of-year] (quot (- day-of-year 1) 28))

(defn gregorian-to-parts [gregorian-date]
  {:day (.get (.dayOfYear gregorian-date))
   :year (.get (.year gregorian-date))})

(defn gormanic-to-parts [gormanic]
  {:day
         (if [:intermission gormanic]
          365
          (.indexOf gormanic.core/gormanic-months
                    (+
                      (* 28 (:month gormanic))
                      (:day gormanic)))
          )
   :year (:year gormanic)})

(defn gormanic-to-string [gormanic]
  (if (:intermission gormanic)
    (str "Intermission " (:year gormanic))
    (str (:day gormanic) " " (gormanic-months (:month gormanic)) " " (:year gormanic))))

(defn parts-to-gormanic [parts]
  (let [day (:day parts)]
    (if (>= day 365)
      {:year (:year parts) :intermission true}
      {:year (:year parts)
       :month (gormanic-month-index day)
       :day (gormanic-day-index day)})))

(defn convert
  [gregorian]
  (gormanic-to-string
    (parts-to-gormanic
      (gregorian-to-parts gregorian))))
