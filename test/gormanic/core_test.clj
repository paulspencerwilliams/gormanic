(ns gormanic.core-test
  (:require [clojure.test :refer :all]
            [gormanic.core :refer :all]
            [clj-time.core :as t]))

(deftest simple-case
  (testing "1 January 2015"
    (is (= "1 March 2015" (convert (t/date-time 2015 1 1))))))
