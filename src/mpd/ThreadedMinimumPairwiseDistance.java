package mpd;

public class ThreadedMinimumPairwiseDistance implements MinimumPairwiseDistance {

    @Override
    public int minimumPairwiseDistance(int[] values) {
    	// Create a new Min object and the 4 threads to cover the 4 triangles
    	Min min = new Min();
    	Thread lowerLeft = new Thread(new lowerLeft(values, min));
    	Thread bottomRight = new Thread(new bottomRight(values, min));
    	Thread topRight = new Thread(new topRight(values, min));
    	Thread Center = new Thread(new Center(values, min));

    	// Start all 4 threads
    	lowerLeft.start();
    	bottomRight.start();
    	topRight.start();
    	Center.start();

    	
    	// join() all 4 threads
    	try {
    		lowerLeft.join();
    		bottomRight.join();
    		topRight.join();
    		Center.join();
    	} catch (InterruptedException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
        	
        
        
        return min.getMin();
    }
    
    // Four classes representing the four different segments in finding the minimum pairwise distance
    
    private class lowerLeft implements Runnable {
    	private int[] values;
    	Min min;
    	
    	private lowerLeft(int[] values, Min min) {
    		this.values = values;
    		this.min = min;
    	}
    	
		@Override
		public void run() {
			int len = values.length;
			int toCheck = Integer.MAX_VALUE;
			
			for (int i = 0; i < len/2; i++) {
				for (int j = 0; j < i; j++) {
					if (Math.abs(values[i] - values[j]) < toCheck) {
						toCheck = Math.abs(values[i] - values[j]);
					}
				}
			}	
			
			min.setMin(toCheck);
		}
		
		
    }

    private class bottomRight implements Runnable {
    	private int[] values;
    	Min min;

    	private bottomRight(int[] values, Min min) {
    		this.values = values;
    		this.min = min;
    	}

    	@Override
    	public void run() {
    		int len = values.length;
    		int toCheck = Integer.MAX_VALUE;

    		for (int i = len/2; i < len; i++) {
    			for (int j = 0; j < i - (len/2); j++) {
    				if (Math.abs(values[i] - values[j]) < toCheck) {
    					toCheck = Math.abs(values[i] - values[j]);
    				}
    			}
    		}	

    		min.setMin(toCheck);
    	}


    }

    private class topRight implements Runnable {
    	private int[] values;
    	Min min;
    	
    	private topRight(int[] values, Min min) {
    		this.values = values;
    		this.min = min;
    	}
    	
		@Override
		public void run() {
			int len = values.length;
			int toCheck = Integer.MAX_VALUE;
			
			for (int i = len/2; i < len; i++) {
				for (int j = len/2; j < i; j++) {
					if (Math.abs(values[i] - values[j]) < toCheck) {
						toCheck = Math.abs(values[i] - values[j]);
					}
				}
			}	
			
			min.setMin(toCheck);
		}
		
		
    }
    
    private class Center implements Runnable {

    	private int[] values;
    	Min min;
    	
    	private Center(int[] values, Min min) {
    		this.values = values;
    		this.min = min;
    	}
    	
		@Override
		public void run() {
			int len = values.length;
			int toCheck = Integer.MAX_VALUE;
			
			for (int i = len/2; i < len; i++) {
				for (int j = i - (len/2); j < len/2; j++) {
					if (Math.abs(values[i] - values[j]) < toCheck) {
						toCheck = Math.abs(values[i] - values[j]);
					}
				}
			}	
			
			min.setMin(toCheck);
		}
		
		
    }
   
    // Class to hold the minimum pairwise distance
    
    private class Min {
    	private int min = Integer.MAX_VALUE;
    	
    	public synchronized void setMin(int toCheck) {
    		if (toCheck < this.min) {
    			this.min = toCheck;
    		}
    		
    	}
    	
    	public int getMin() {
    		return this.min;
    	}
    	
    }

}
