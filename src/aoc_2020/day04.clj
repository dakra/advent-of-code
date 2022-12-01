(ns aoc-2020.day04
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [malli.core :as m]))

(def raw-demo-input "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd
byr:1937 iyr:2017 cid:147 hgt:183cm

iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884
hcl:#cfa07d byr:1929

hcl:#ae17e1 iyr:2013
eyr:2024
ecl:brn pid:760753108 byr:1931
hgt:179cm

hcl:#cfa07d eyr:2025 pid:166559648
iyr:2011 ecl:brn hgt:59in")

;; byr (Birth Year)
;; iyr (Issue Year)
;; eyr (Expiration Year)
;; hgt (Height)
;; hcl (Hair Color)
;; ecl (Eye Color)
;; pid (Passport ID)
;; cid (Country ID)

(def valid-fields #{:byr :iyr :eyr :hgt :hcl :ecl :pid})

(defn parse-input [input]
  (->> (str/split input #"\R\R")
       (map (partial re-seq #"(\w{3}):(\S+)"))
       (map #(into {} (map (fn [[_ k v]] [(keyword k) v]) %)))))

(def demo-input (parse-input raw-demo-input))


(->> demo-input
     (map (fn [m]
            (= (count (filter identity (map #(% m) valid-fields))) 7)))
     (filter identity)
     count)

(->> demo-input
     (map (fn [m]
            (count (filter identity (map #(% m) valid-fields)))))
     )
;; => 2


(def real-input (-> "2020/day04.txt" io/resource slurp parse-input))

(->> real-input
     (map (fn [m]
            (= (count (filter identity (map #(% m) valid-fields))) 7)))
     (filter identity)
     count)
;; => 206

;; Part 2


;; - byr (Birth Year) - four digits; at least 1920 and at most 2002.
;; - iyr (Issue Year) - four digits; at least 2010 and at most 2020.
;; - eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
;; - hgt (Height) - a number followed by either cm or in:
;;   - If cm, the number must be at least 150 and at most 193.
;;   - If in, the number must be at least 59 and at most 76.
;; - hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
;; - ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
;; - pid (Passport ID) - a nine-digit number, including leading zeroes.
;; - cid (Country ID) - ignored, missing or not.

(def schema [:map
             [:byr [:fn (fn [v] (<= 1920 (Long/parseLong v) 2002))]]
             [:iyr [:fn (fn [v] (<= 2010 (Long/parseLong v) 2020))]]
             [:eyr [:fn (fn [v] (<= 2020 (Long/parseLong v) 2030))]]
             [:hgt [:fn (fn [v] (let [[height unit] (re-find #"(\d+)(cm|in)")]
                                  (case unit
                                    "cm" (<= 150 (Long/parseLong height) 193)
                                    "in" (<= 59 (Long/parseLong height) 76)
                                    :default false)))]]
             [:hcl [:re #"^#[0-9a-f]{6}$"]]
             [:ecl [:enum "amb" "blu" "brn" "gry" "grn" "hzl" "oth"]]
             [:pid [:re #"^\d{9}$"]]
             [:cid {:optional true} any?]])

(def valid? (m/validator schema))

(filter identity (map valid? real-input))

(m/explain schema (first real-input))
