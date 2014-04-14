package com.lsu.beambreaker;

public class Model
{
	private static final int A = 0;
	private static final int B = 1;
	private static final int C = 2;
	private static final int D = 3;
	private static final int E = 4;
	private static final int F = 5;
	private static final int G = 6;
	private static final int H = 7;
	private static final int I = 8;
	private static final int J = 9;
	private static final int K = 10;
	private static final int L = 11;
	private static final int M = 12;
	private static final int N = 13;
	private static final int O = 14;
	private static final int P = 15;
	private static final int Q = 16;
	private static final int R = 17;
	private static final int S = 18;
	private static final int T = 19;
	private static final int U = 20;
	private static final int V = 21;
	private static final int W = 22;
	private static final int X = 23;
	private static final int Y = 24;
	private static final int Z = 25;
	static int NODES = 11; //Number of starting nodes
	int adjList[][] = new int[NODES][];//[NODES-1]; list of nodes adjacant to
	int[][] edge = new int[NODES][];//[NODES-1]; list of edge NAMES, corresponds to adjList at same index
	
	public Model()
	{
		

		//Level 1 model
		adjList[A] = new int[]{B, D};
		edge[A] = new int[]{2, 1};
		adjList[B] = new int[] {A, C, E};
		edge[B] = new int[] {2, 3, 1};
		adjList[C] = new int[] {B, G, K};
		edge[C] = new int[] {3, 1, 4};
		adjList[D] = new int[] {A, E, F, H};
		edge[D] = new int[] {1, 2, 3, 4};
		adjList[E] = new int[] {B, D, G};
		edge[E] = new int[] {1, 2, 3};
		adjList[F] = new int[] {D, G, I};
		edge[F] = new int[] {3, 2, 4};
		adjList[G] = new int[] {C, E, F, J};
		edge[G] = new int[] {1, 3, 2, 4};
		adjList[H] = new int[] {D, I};
		edge[H] = new int[] {4, 3};
		adjList[I] = new int[] {F, H, J};
		edge[I] = new int[] {4, 3, 2};
		adjList[J] = new int[] {G, I, K};
		edge[J] = new int[] {4, 2, 1};
		adjList[K] = new int[] {C, J};
		edge[K] = new int[] {4, 1};
	}
	
	void lookForEdgesToLose(int line)
	{
		for (int i = 0; i < V; i++)
		{
			for (int j = 0; i < edge[i].length; j++)
				if (edge[i][j] == line) fuseNodes(i, adjList[i][j]);
		}
	}
	
	void fuseNodes(int a, int b)
	{
		//Give all of b's edges to a
		for (int i = 0; i < adjList[b].length; i++)
		{
			if (adjList[b][i] != a && adjList[b][i] != -1)
			{
				adjList[a][adjList[a].length + i] = adjList[b][i];
				edge[a][edge[a].length + i] = edge[b][i];
				
				//Mark b as dead
				adjList[b][i] = -1;
				edge[b][i] = -1;
			}
		}
	}
}
