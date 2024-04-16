const consts = {
    "x": new Variable("x"),
    "y": new Variable("y"),
    "z": new Variable("z")
}

const Universal = {
    toString() {
        return this.arguments.join(" ") + " " + this.def
    },
    evaluate(x, y, z) {
        if (this.arguments.length === 0) {
            return 0
        }
        const newArgs = this.arguments.map((ens) => ens.evaluate(x, y, z));
        return this.op(...newArgs)
    },
    prefix() {
        return "(" + this.def + " " + this.arguments.map(arg => arg.prefix()).join(" ") + ")"
    }
}

function Variable(a) {
    return {
        arg: a,
        evaluate: function (x, y, z) {
            switch (this.arg) {
                case "x" :
                    return eval(x);
                case "y" :
                    return eval(y);
                case "z" :
                    return eval(z);
            }
        },
        toString: function () {
            return this.arg
        },
        prefix: function () {
            return this.arg
        }
    }
}

function Const(a) {
    return {
        arg: a,
        evaluate: function () {
            return eval(this.arg);
        },
        toString: function () {
            return this.arg.toString()
        },
        prefix: function () {
            return this.arg.toString();
        }
    }
}

let mapsF = {}

function fCreation(def, f, nums) {
    const eq = function (...args) {
        this.arguments = args
    }
    eq.prototype = Object.create(Universal)
    eq.prototype.def = def
    eq.prototype.op = f
    mapsF[def] = Array.of(eq, nums)
    return eq;
}

const ArcTan = fCreation("atan", Math.atan, 1)
const ArcTan2 = fCreation("atan2", Math.atan2, 2)
const Add = fCreation("+", (...args) => args[0] + args[1], 2)
const Subtract = fCreation("-", (...args) => args[0] - args[1], 2)
const Divide = fCreation("/", (...args) => args[0] / args [1], 2)
const Multiply = fCreation("*", (...args) => args[0] * args[1], 2)
const Negate = fCreation("negate", (...args) => -eval(args[0]), 1)
const Sum = fCreation("sum", (...args) => args.reduce((a, b) => a + b), 0)
const Avg = fCreation("avg", ((...args) => args.reduce((a, b) => a + b) / args.length), 0)

function ElementsExc(message = "") {
    this.name = "ElementsExc";
    this.message = message;
}

ElementsExc.prototype = Error.prototype;

function BracketsExc(message = "") {
    this.name = "BracketsExc";
    this.message = message;
}

BracketsExc.prototype = Error.prototype;

function parse(string) {
    string = string.replace(/\s+/g, ' ').trim().split(" ")
    for (let i = 0; i < string.length; i++) {
        let curr = string[i]
        if (curr in mapsF) {
            const temp = mapsF[curr]
            let res = []
            for (let pos = 0; pos < temp[1]; pos++) {
                res[pos] = string[i - temp[1] + pos]
            }
            string[i] = new temp[0](...res)
            string.splice(i - temp[1], temp[1]);
            i -= temp[1];
        } else if (!isNaN(Math.sign(curr))) {
            string[i] = new Const(parseInt(curr))
        } else {
            string[i] = new Variable(curr)
        }
    }
    return string[string.length - 1];
}

function parsePrefix(string) {
    string = string.replace(/\(/g, " ( ").replace(/\)/g, " ) ").replace(/ +/g, ' ').trim()
    if (string.length === 0) {
        throw new ElementsExc("No elements, dude")
    }
    string = string.split(" ")
    let stack = []
    let cnt = 0;
    for (let i = string.length - 1; i >= 0; i--) {
        const curr = string[i];
        if (!isNaN(Math.sign(curr))) {
            stack.push(new Const(curr))
        } else if (curr in consts) {
            stack.push(consts[curr])
        } else if (curr in mapsF) {
            if (i !== 0 && string[i - 1] !== "(") {
                throw new BracketsExc("Wrong bracket sequence. Bracket \"(\" should be at " + (i - 1))
            }

            const defs = mapsF[curr]
            const memor = stack.filter(item => item !== ")")
            if (memor.length < defs[1]) {
                throw new ElementsExc("Elements exception in ->" + string + "<-\n" +
                    "There should be " + defs + " args. There's " + stack.length + "arguments")
            }
            let res = []
            if (defs[1] === 0) {
                let point = 0

                while (stack.length !== 0) {
                    const current = stack.pop()
                    if (current === ")") {
                        stack.push(current)
                        break
                    }
                    res[point] = current
                    point++;
                }
            } else {
                for (let i = 0; i < defs[1]; i++) {
                    let current = stack.pop()
                    if (current !== ")") {
                        res[i] = (current)
                    } else {

                        throw new ElementsExc("WHAT1?")
                    }
                }
            }
            stack.push(new defs[0](...res))
        } else if (curr === "(") {
            cnt--;
            if (cnt < 0) {
                throw new BracketsExc("Brackets error ->" + string + "<-\n" + "The balance: " + cnt)
            }
            if (!(string[i + 1] in mapsF)) {
                throw new ElementsExc("Wrong input format: \n" + "Brackets should be used only for functions \n" +
                    "in expr ->" + string.join(" ") + "<-")
            }
            let currSymbol = stack.pop()
            if (stack.pop() !== ")") {
                throw new ElementsExc("WHAT?")
            } else {
                stack.push(currSymbol)
            }
        } else if (curr === ")") {
            cnt++;
            stack.push(")")
        } else {
            throw new ElementsExc("Unknown element ->" + curr + "<-")
        }
    }
    if (cnt !== 0) {
        throw new BracketsExc("Brackets error in ->" + string + "<-\n" + "The balance: " + cnt)
    }
    stack = stack.filter(item => item !== ")")
    if (stack.length !== 1) {
        throw new ElementsExc("Not enough operators in ->" + string + "<-\n" +
            "There should be at least " + stack.length / 2 + " more")
    }
    return stack.pop();
}
