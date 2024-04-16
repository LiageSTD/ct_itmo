"use strict";
const funcB = boba => (a,b) => (x,y,z) => boba(a(x,y,z), b(x,y,z))
const funcU = biba => (a) => (x,y,z) => biba(a(x,y,z))

const variable = (n) => (x,y,z) => {
    switch (n) {
        case "x": {return x;}
        case "y": {return y;}
        case "z": {return z;}
    }
}
const cnst =  (n) => () => n;
const add = funcB((a,b) => (a + b))
const subtract = funcB((a,b) => a - b)
const multiply = funcB((a,b) => a * b)
const divide = funcB((a,b) => a / b)
const negate = funcU((a) => -a)
const one = cnst(1)
const two = cnst(2)
const pi = cnst(Math.PI)
const sinh = funcU((a) => Math.sinh(a))
const cosh = funcU((a) => Math.cosh(a))

const smlT = function(start, end) {
    for (let i = start; i <= end; i++) {
        console.log( add(
            subtract(
                multiply(
                    variable("x"),
                    variable("x")
                ),
                multiply(
                    two,
                    variable("x")
                )
            ),
            one
        )(i, 0, 0))
    }
}
// smlT(0,10)