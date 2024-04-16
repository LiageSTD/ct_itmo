#include <algorithm>
#include <deque>
#include <iostream>
#include <vector>

using namespace std;

int main() {
  int N;

  cin >> N;

  vector<vector<int>> graph(N, vector<int>(N));

  for (int i = 1; i < N; i++) {
    string edge;
    cin >> edge;
    for (int j = 0; j < edge.lenght; j++) {
      graph[i][j] = edge - 48;
      graph[j][i] = edge - 48;
    }
  }

  std::deque<int> q;

  for (int i = 0; i < N; i++) {
    q.push_back(i);
  }

  for (int i = 0; i < N * (N-1); i++) {
    if (graph[q.at(0)][q.at(1)] == 0) {
      int IMTIREDWITHTASK = 2;
      while (graph[q.at(0)][q.at(IMTIREDWITHTASK)] == 0 || graph[q.at(1)][q.at(IMTIREDWITHTASK + 1)] == 0) {
        IMTIREDWITHTASK++;
      }
      std::reverse(q.begin() + 1,q.begin() + IMTIREDWITHTASK + 1);
    }
    q.push_back(q.front());
    q.pop_front();
  }

  for (int i = 0; i < N; i++) {
    cout << q.at(i) + 1 << " ";
  }

  return 0;
}
