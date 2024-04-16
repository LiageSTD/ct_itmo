use std::io;

fn main() {
    let mut n = String::new();
    io::stdin().read_line(&mut n).unwrap();
    let n: usize = n.trim().parse().unwrap();

    let mut graph = vec![vec![false; n]; n];

    for i in 0..n {
        let mut s = String::new();
        io::stdin().read_line(&mut s).unwrap();
        let s = s.trim();

        for (j, c) in s.chars().enumerate() {
            if j < i && c == '1' {
                graph[i][j] = true;
                graph[j][i] = true;
            }
        }
    }

    let mut ans: Vec<usize> = (0..n).collect();

    for _ in 0..n * (n - 1) {
        if !graph[ans[0]][ans[1]] {
            let mut j = 2;
            while !(graph[ans[0]][ans[j]] && graph[ans[1]][ans[j + 1]]) {
                j += 1;
            }
            ans[1..=j].reverse();
        }

        let first = ans.remove(0);
        ans.push(first);
    }

    for i in 0..n {
        print!("{} ", ans[i] + 1);
    }
}
