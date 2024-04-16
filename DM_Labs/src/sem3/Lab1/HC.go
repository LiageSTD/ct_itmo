package main

import (
	"fmt"
)

type Graph struct {
	matrix  [][]bool
	vertices []int
	size    int
}

func newGraph(size int) *Graph {
	matrix := make([][]bool, size+1)
	for i := range matrix {
		matrix[i] = make([]bool, size+1)
	}

	vertices := make([]int, 0, size)

	return &Graph{matrix, vertices, size}
}

func (g *Graph) addEdge(i, j int, flag bool) {
	g.matrix[i][j] = flag
	g.matrix[j][i] = flag
}

func (g *Graph) addVertex(i int) {
	g.vertices = append(g.vertices, i)
}

func (g *Graph) getByDirak() (int, bool) {
	for i := 2; i < len(g.vertices); i++ {
		if g.matrix[g.vertices[0]][g.vertices[i]] && g.matrix[g.vertices[1]][g.vertices[i+1]] {
			return i, true
		}
	}
	return 0, false
}

func (g *Graph) getByChvatal() int {
	for i := 1; i < len(g.vertices); i++ {
		if g.matrix[g.vertices[0]][g.vertices[i]] {
			return i
		}
	}
	return -1
}

func (g *Graph) findHamiltonianCycle() {
	for counter := 0; counter < g.size*(g.size-1); counter++ {
		if g.matrix[g.vertices[0]][g.vertices[1]] {
			g.vertices = append(g.vertices, g.vertices[0])
			g.vertices = g.vertices[1:]
		} else {
			i, found := g.getByDirak()
			if found {
				for j := 0; ; j++ {
					if 1+j < i-j {
						g.vertices[1+j], g.vertices[i-j] = g.vertices[i-j], g.vertices[1+j]
					} else {
						break
					}
				}
				g.vertices = append(g.vertices, g.vertices[0])
				g.vertices = g.vertices[1:]
			} else {
				i = g.getByChvatal()
				for j := 0; ; j++ {
					if 1+j < i-j {
						g.vertices[1+j], g.vertices[i-j] = g.vertices[i-j], g.vertices[1+j]
					} else {
						break
					}
				}
				g.vertices = append(g.vertices, g.vertices[0])
				g.vertices = g.vertices[1:]
			}
		}
	}
}

func main() {
	var n int
	fmt.Scan(&n)
	g := newGraph(n)
	currLine := ""

	for i := 1; i < n+1; i++ {
		var c byte
		g.addVertex(i)
		for j := 1; j < i; j++ {
			if j == 1 {
				fmt.Scan(&currLine)
			}
			c = currLine[j-1]
			g.addEdge(i, j, c != '0')
		}
	}

	g.findHamiltonianCycle()
	vertices := g.vertices
	for _, item := range vertices {
		fmt.Print(item, " ")
	}
}
