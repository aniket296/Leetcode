class Solution {
    private static class Server {
        int weight;
        int index;
        long freeTime;
        Server(int weight, int index, long freeTime) {
            this.weight = weight;
            this.index = index;
            this.freeTime = freeTime;
        }
    }
    public int[] assignTasks(int[] servers, int[] tasks) {
        int n = servers.length;
        int m = tasks.length;
        int[] ans = new int[m];
        PriorityQueue<Server> freeServers = new PriorityQueue<>((a, b) -> {
            if (a.weight != b.weight) {
                return Integer.compare(a.weight, b.weight);
            }
            return Integer.compare(a.index, b.index);
        });
        PriorityQueue<Server> busyServers = new PriorityQueue<>((a, b) -> {
            if (a.freeTime != b.freeTime) {
                return Long.compare(a.freeTime, b.freeTime);
            }
            if (a.weight != b.weight) {
                return Integer.compare(a.weight, b.weight);
            }
            return Integer.compare(a.index, b.index);
        });
        for (int i = 0; i < n; i++) {
            freeServers.offer(new Server(servers[i], i, 0));
        }
        long currentTime = 0;
        for (int i = 0; i < m; i++) {
            currentTime = Math.max(currentTime, i);
            if (freeServers.isEmpty()) {
                currentTime = Math.max(currentTime, busyServers.peek().freeTime);
            }
            while (!busyServers.isEmpty() && busyServers.peek().freeTime <= currentTime) {
                freeServers.offer(busyServers.poll());
            }
            Server assigned = freeServers.poll();
            ans[i] = assigned.index;
            assigned.freeTime = currentTime + tasks[i];
            busyServers.offer(assigned);
        }
        return ans;
    }
}