(ns aoc-2020.day01
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def demo-input "1721
979
366
299
675
1456")

(defn parse-long [num]
  (Long/parseLong num))

(defn parse-input [input]
  (->> input
       str/split-lines
       (map parse-long)))

(def input (parse-input (slurp (io/resource "2020/day01.txt"))))

(defn day01 [input]
  (first (for [x input, y input
               :when (< x y)
               :when (= (+ x y) 2020)]
           (* x y))))


(day01 (parse-input demo-input))
;; => 514579

(day01 input)
;; => 1018944

(defn day01-b [input]
  (first (for [x input, y input, z input
               :when (< x y z)
               :when (= (+ x y z) 2020)]
           (* x y z))))

(day01-b (parse-input demo-input))
;; => 241861950

(day01-b input)
;; => 8446464
