(ns aoc-2020.day03
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(def raw-demo-input "..##.......
#...#...#..
.#....#..#.
..#.#...#.#
.#...##..#.
..#.##.....
.#.#.#....#
.#........#
#.##...#...
#...##....#
.#..#...#.#")

(def demo-input (str/split-lines demo-input))
(def real-input (-> "2020/day03.txt" io/resource slurp str/split-lines))


(->> (map (fn [row x]
            (= (nth row (mod x (count row))) \#))
          real-input
          (range 0 99999 3))
     (filter true?)
     count)
;; => 148
;; => 7


;; Part 2

(def slopes [[1 1] [3 1] [5 1] [7 1] [1 2]])

(defn sled [input [right down]]
  (->> (map (fn [[row & _] x]
              (= (nth row (mod x (count row))) \#))
            (partition-all down input)
            (range 0 99999 right))
       (filter true?)
       count))

(reduce * (map (partial sled real-input) slopes))
;; => 727923200
;; => 336
