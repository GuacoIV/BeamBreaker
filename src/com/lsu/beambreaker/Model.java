package com.lsu.beambreaker;

import android.content.Context;
import android.widget.Toast;

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
	static int NODES; //Number of starting nodes
	int adjList[][];//[][] = new int[NODES][];//[NODES-1]; list of nodes adjacant to
	int edge[][]; //= new int[NODES][];//[NODES-1]; list of edge NAMES, corresponds to adjList at same index
	int startNode;
	int endNode;
	Context context;
	
	public Model(Context context, int level)
	{
		this.context = context;
		switch (level)
		{
			//Level 1 model
			case 1:
				NODES = 11;
				adjList = new int[NODES][];
				edge = new int[NODES][];
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
				startNode = B;
				endNode = H;
				break;
			case 2:
				NODES = 16;
				adjList = new int[NODES][];
				edge = new int[NODES][];
				adjList[A] = new int[]{B, E};
				edge[A] = new int[]{1, 4};
				adjList[B] = new int[]{A, C, F};
				edge[B] = new int[]{1, 2, 4};
				adjList[C] = new int[]{B, D, G};
				edge[C] = new int[]{2, 3, 4};
				adjList[D] = new int[]{C, H};
				edge[D] = new int[]{3, 4};
				adjList[E] = new int[]{A, F, J};
				edge[E] = new int[]{4, 1, 5};
				adjList[F] = new int[]{B, E, G, K};
				edge[F] = new int[]{4, 1, 2, 5};
				adjList[G] = new int[]{C, F, H};
				edge[G] = new int[]{4, 2, 3};
				adjList[H] = new int[]{D, G, I, M};
				edge[H] = new int[]{4, 3, 2, 5};
				adjList[I] = new int[]{F, H, L};
				edge[I] = new int[]{3, 2, 5};
				adjList[J] = new int[]{E, K, N, O};
				edge[J] = new int[]{5, 1, 6, 3};
				adjList[K] = new int[]{F, J, L};
				edge[K] = new int[]{5, 1, 3};
				adjList[L] = new int[]{I, K, M, O};
				edge[L] = new int[]{5, 3, 2, 1};
				adjList[M] = new int[]{H, L};
				edge[M] = new int[]{5, 2};
				adjList[N] = new int[]{J, P};
				edge[N] = new int[]{6, 3};
				adjList[O] = new int[]{J, L, P};
				edge[O] = new int[]{3, 1, 6};
				adjList[P] = new int[]{N, O};
				edge[P] = new int[]{3, 6};
				startNode = D;
				endNode = J;
		}
	}
	
	void lookForEdgesToLose(int line)
	{
		for (int i = 0; i < NODES; i++)
		{
			for (int j = 0; j < edge[i].length; j++)
			{
				if (edge[i][j] == line)
				{
					fuseNodes(i, adjList[i][j]);
					break;
				}
			}
		}
	}
	
	void fuseNodes(int a, int b)
	{
		//Give all of b's edges to a
		
		//Holding area
		int tempAdjList[] = new int[adjList[a].length + adjList[b].length];
		int tempEdge[] = new int[edge[a].length + edge[b].length];
		
		//Get a's current values
		for (int i = 0; i < adjList[a].length; i++)
		{
			tempAdjList[i] = adjList[a][i];
			tempEdge[i] = edge[a][i];
		}
		
		for (int i = 0; i < adjList[b].length; i++)
		{
			//Bug: when adjList[b][i] == a, we end up with a duplicate
			if (adjList[b][i] != -1)
			{
				tempAdjList[adjList[a].length + i] = adjList[b][i];
				tempEdge[edge[a].length + i] = edge[b][i];
				
				//if b had the start or end circle, give it to a
				if (startNode==b) startNode = a;
				if (endNode==b) endNode = a;
				
				//Check for win
				if (startNode==endNode) 
				{
					showWinMessage();
					break;
				}
				
				//Mark b as dead
				adjList[b][i] = -1;
				edge[b][i] = -1;
			}
		}
		
		//Give temp values to adjList and edge, allocate new space
		adjList[a] = new int[tempAdjList.length];
		edge[a] = new int[tempEdge.length];
		for (int i = 0; i < tempAdjList.length; i++)
		{
			adjList[a][i] = tempAdjList[i];
			edge[a][i] = tempEdge[i];
		}
	}
	
	void showWinMessage()
	{
		Toast winMessage = Toast.makeText(context, "You win!!!", Toast.LENGTH_LONG);
		winMessage.show();
	}
}
