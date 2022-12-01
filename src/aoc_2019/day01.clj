(ns aoc-2019.day01
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input
  (->> "2019/day01.txt" io/resource slurp str/split-lines (map parse-long)))

(defn required-fuel [m]
  (- (quot m 3) 2))


;; Part 1

(apply + (map required-fuel input))
;; => 3403509


;; Part 2

(defn require-fuel-rec [m]
  (loop [m* m, acc 0]
    (let [x (required-fuel m*)]
      (if (<= x 0)
        acc
        (recur x (+ acc x))))))

(require-fuel-rec 1969)  ;; => 966
(require-fuel-rec 100756) ;; => 50346

(apply + (map require-fuel-rec input))
;; => 5102369
