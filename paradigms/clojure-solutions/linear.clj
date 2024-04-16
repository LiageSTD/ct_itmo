(defn vectors [f]
  (fn [& args]
    (apply mapv f args)))
(def v+ (vectors +))
(def v- (vectors -))
(def vd (vectors /))
(def v* (vectors *))
(defn v*m [v m]
  (mapv #(* % m) v))
(defn v*s [v s]
  (mapv #(* % s) v))
(defn spc [a b num1 num2]
  (- (* (get-in a [num1]) (get-in b [num2])) (* (get-in a [num2]) (get-in b [num1]))))
(defn vect [a b]
  (vector (spc a b 1 2), (spc a b 2 0), (spc a b 0 1)))
(defn scalar [& args] (apply + (apply mapv * args)))
(defn matrix [f]
  (fn [& args]
    (apply mapv (vectors f) args )))
(def m+ (matrix +))
(def m- (matrix -))
(def m* (matrix *))
(def md (matrix /))
(defn m*s [m s]
  (mapv #(v*m % s) m))
(defn m*v [m v]
  (mapv #(scalar % v) m))
(defn transpose [m]
  (apply mapv vector m))
(defn m*m [m1 m2]
  (transpose (mapv #(m*v m1 %) (transpose m2))))
(defn shapelss [func]
  (fn [arg1 arg2] (if (vector? arg1) (mapv (shapelss func) arg1 arg2) (func arg1 arg2))))
(def s* (shapelss *))
(def s+ (shapelss +))
(def sd (shapelss /))
(def s- (shapelss -))
(print (s+ 5.3 6.8))