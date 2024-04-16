const fs = require('fs');
const readline = require('readline');

const name = "destroy";
const input = fs.readFileSync(`${name}.in`, 'utf-8').split('\n');
const output = fs.createWriteStream(`${name}.out`);

const sc = readline.createInterface({
    input: fs.createReadStream(`${name}.in`),
    output: process.stdout,
    terminal: false
});

let lineIterator = 0;

function readLine() {
    return input[lineIterator++];
}

function main() {
    const first = readLine().split(' ').map(Number);
    const n = first[0];
    const m = first[1];
    let s = BigInt(first[2]);
    const edges = Array(m).fill(0).map((_, i) => new Edge(i));
    edges.sort((a, b) => {
        if (a.w === b.w) {
            return 0;
        } else {
            return a.w < b.w ? 1 : -1;
        }
    });


    const dsu = new DSU(n);
    const used = Array(m).fill(true);

    for (const e of edges) {
        if (dsu.parent(e.u) !== dsu.parent(e.v)) {
            used[e.i] = false;
            dsu.merge(e.u, e.v);
        }
    }

    const ans = new Set();

    for (const edge of edges.reverse()) {
        if (used[edge.i] && s >= edge.w) {
            s -= edge.w;
            ans.add(edge.i + 1);
        }
    }

    output.write(`${ans.size}\n`);
    output.write(Array.from(ans).join(' '));
}

class DSU {
    constructor(n) {
        this.p = Array(n).fill(0).map((_, i) => i);
        this.sz = Array(n).fill(1);
    }

    parent(x) {
        if (x !== this.p[x]) {
            this.p[x] = this.parent(this.p[x]);
        }
        return this.p[x];
    }

    merge(u, v) {
        let rootU = this.parent(u);
        let rootV = this.parent(v);

        if (this.sz[rootU] > this.sz[rootV]) {
            this.sz[rootU] += this.sz[rootV];
            this.p[rootV] = rootU;
        } else {
            this.sz[rootV] += this.sz[rootU];
            this.p[rootU] = rootV;
        }
    }
}

class Edge {
    constructor(i) {
        const parts = readLine().split(' ').map(Number);
        this.i = i;
        this.u = parts[0] - 1;
        this.v = parts[1] - 1;
        this.w = BigInt(parts[2]);
    }
}


main();
