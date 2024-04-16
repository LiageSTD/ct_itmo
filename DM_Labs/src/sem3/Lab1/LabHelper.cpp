#include <iostream>
#include <vector>
std::vector<std::vector<long long>> matrix(5, std::vector<long long>(5, 0));

int n = 5;

std::vector<std::vector<long long>> multiply(std::vector<std::vector<long long>> m1, std::vector<std::vector<long long>> m2) {
    std::vector<std::vector<long long>> res(5, std::vector<long long>(5, 0));
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            for (int k = 0; k < n; k++) {
                res[i][j] += m1[i][k] * m2[k][j];
            }
            res[i][j] = res[i][j] % 1000000007;
        }
    }
    return res;
}

std::vector<std::vector<long long>> pow(std::vector<std::vector<long long>> m, long long a) {
    if (a == 1) {
        return m;
    } else if (a % 2 == 0) {
        return pow(multiply(m, m), a / 2);
    } else {
        return multiply(m, pow(m, a - 1));
    }
}

int main() {
    matrix[0][1] = 1;
    matrix[1][2] = 1;
    matrix[2][3] = 1;
    matrix[3][0] = 1;
    matrix[0][4] = 1;
    matrix[4][3] = 1;

    std::vector<std::vector<long long>> res = pow(matrix, 11986126526701);
    std::cout << res[0][1];

    return 0;
}