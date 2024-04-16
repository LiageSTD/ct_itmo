(defn universal [f]
  (fn [& args] (fn [maps] (apply f (map #(% maps) args))))
)
(def add (universal +))
(def subtract (universal -))
(def divide (universal (fn [a b] (/ a (double b)))))
(def multiply (universal *))
(def negate (universal -))
(def arcTan (universal (fn [a] (Math/atan a))))
(def arcTan2 (universal (fn [a b] (Math/atan2 a (double b)))))
(defn constant [x]
  (fn [& _] x)
)
(defn variable [var]
  (fn [maps]
    (maps (str var))
  )
)
(def MapF {
           '+      add
           '-      subtract
           'negate negate
           '/      divide
           '*      multiply
           'atan   arcTan
           'atan2  arcTan2
           }
)

(defn parseImpl [input, Con, Var, MyMap]
  (if (number? input) (Con input)
    (if (symbol? input) (Var (str input))
      (apply (MyMap (first input)) (mapv (fn [ElOfInput] (parseImpl ElOfInput Con Var MyMap)) (rest input)))
    )
  )
)
(defn parseFunction [input]
  (let [input (read-string input)]
    (parseImpl input constant variable MapF)
  )
)
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(load-file "proto.clj")

(def _op (field :op))
(def _opToString (field :opToString))
(def _const (field :const))
(def _args (field :args))
(def evaluate (method :evaluate))
(def toString (method :toString))

(defn UniversalInterface [evaluate toString]
  {
   :evaluate evaluate
   :toString toString
   }
)

(def Universal
  (constructor
    (fn [this, op, opToString] (assoc this :op op :opToString opToString))
    (UniversalInterface
      (fn [this vars] (apply (_op this) (map #(evaluate % vars) (_args this))))
      (fn [this] (str "(" (_opToString this) " " (clojure.string/join " " (mapv toString (_args this))) ")"))
    )
  )
)

(defn BiFab [op, opToString] (constructor (fn [this & args] (assoc this :args args)) (Universal op opToString)))

(defn CAndV [func]
  (constructor
    (fn [this, _const] (assoc this :const _const))
    (UniversalInterface
      func
      (fn [this] (str (_const this)))
    )
  )
)
(def Variable (CAndV (fn [this vars] (double (vars (_const this))))))
(def Constant (CAndV (fn [this _] (double (_const this)))))
(def Add (BiFab + "+"))
(def Subtract (BiFab - "-"))
(def Multiply (BiFab * "*"))
(def Divide (BiFab (fn [a b] (/ a (double b))) "/"))
(def Negate (BiFab - "negate"))
(def Sinh (BiFab (fn [a] (Math/sinh (double a))) "sinh"))
(def Cosh (BiFab (fn [a] (Math/cosh (double a))) "cosh"))

(def MapFO {
            '+      Add
            '-      Subtract
            'negate Negate
            '/      Divide
            '*      Multiply
            'cosh Cosh
            'sinh Sinh
            }
)
(defn parseObject [input]
  (let [input (read-string input)]
    (parseImpl input Constant Variable MapFO)
  )
)