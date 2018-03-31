package cs340.client.model;

import java.util.ArrayList;

public class Player {
	/* Fields */
	private String username;
	private String password;
	private String authToken;
	private ArrayList<TrainCard> cards;
	private ArrayList<DestinationCard> destinations;
	private ArrayList<Route> claimedRoutes;
	private int points;
	private int claimedRoutePoints;
	private int longestRoutePoints;
	private int reachedDestinationPoints;
	private int unreachedDestinationPoints;
	private int trainCars;
	private int currentGame;

	public Player(String u, String p, String a){
		this.username = u;
		this.password = p;
		this.authToken = a;
		this.cards = new ArrayList<TrainCard>();
		this.destinations = new ArrayList<DestinationCard>();
		this.claimedRoutes = new ArrayList<Route>();
		this.points = 0;
		this.claimedRoutePoints = 0;
		this.longestRoutePoints = 0;
		this.reachedDestinationPoints = 0;
		this.unreachedDestinationPoints = 0;
		this.currentGame = -1;
	}

	/* Methods */
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public ArrayList<TrainCard> getCards() {
		return cards;
	}

	public void setCards(ArrayList<TrainCard> cards) {
		this.cards = cards;
	}

	public ArrayList<DestinationCard> getDestinations() {
		return destinations;
	}

	public void setDestinations(ArrayList<DestinationCard> destinations) {
		this.destinations = destinations;
	}

	public ArrayList<Route> getClaimedRoutes() {
		return claimedRoutes;
	}

	public void setClaimedRoutes(ArrayList<Route> claimedRoutes) {
		this.claimedRoutes = claimedRoutes;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getTrainCars() {
		return trainCars;
	}

	public void setTrainCars(int trainCars) {
		this.trainCars = trainCars;
	}

	public int getClaimedRoutePoints() {
		return claimedRoutePoints;
	}

	public void setClaimedRoutePoints(int claimedRoutePoints) {
		this.claimedRoutePoints = claimedRoutePoints;
	}

	public int getLongestRoutePoints() {
		return longestRoutePoints;
	}

	public void setLongestRoutePoints(int longestRoutePoints) {
		this.longestRoutePoints = longestRoutePoints;
	}

	public int getReachedDestinationPoints() {
		return reachedDestinationPoints;
	}

	public void setReachedDestinationPoints(int reachedDestinationPoints) {
		this.reachedDestinationPoints = reachedDestinationPoints;
	}

	public int getUnreachedDestinationPoints() {
		return unreachedDestinationPoints;
	}

	public void setUnreachedDestinationPoints(int unreachedDestinationPoints) {
		this.unreachedDestinationPoints = unreachedDestinationPoints;
	}

	public int getCurrentGame() {
		return currentGame;
	}

	public void setCurrentGame(int currentGame) {
		this.currentGame = currentGame;
	}

	//Discards the equivalent cards from the player's hand
	public void discardTrainCards(ArrayList<TrainCard> selectedCards){
		for(TrainCard c : selectedCards){ //For each of the selected cards
			for(int i = 0; i < this.cards.size(); i++){ //Iterate through the player's hand
				if(this.cards.get(i).getColor().equals(c.getColor())){ //Get the first matching card
					this.cards.remove(i); //Remove it from the player's hand
					break; //Break the inner for loop
				}
			}
		}
	}

	//Calculates which destination cards were achieved
	//Adds points for those achieved and removes points for those that weren't
	public void awardDestinations(){
		for(DestinationCard destination : this.destinations){ //For each destination card
			if(this.completedDestination(destination)){ //If the destination route was completed
				this.points += destination.getPoints(); //Add the points
				this.reachedDestinationPoints += destination.getPoints();
			}else{ //If the destination route wasn't completed
				this.points -= destination.getPoints(); //Subtract the points
				this.unreachedDestinationPoints += destination.getPoints();
			}
		}
	}

	//Calculates whether a specific destination was reached
	public boolean completedDestination(DestinationCard destination){
		String start = destination.getStartPoint();
		String end = destination.getEndPoint();
		boolean containsStart = false;
		boolean containsEnd = false;

		for(Route r : this.claimedRoutes){
			if(r.getEndpoints().get(0).equals(start) || r.getEndpoints().get(1).equals(start)){
				containsStart = true;
			}else if(r.getEndpoints().get(0).equals(end) || r.getEndpoints().get(1).equals(end)){
				containsEnd = true;
			}
		}

		if(!containsStart || !containsEnd){ //If we don't have the start or the end of the destination route in our list of claimed routes, we return false
			return false;
		}

		return this.completedDestinationInner(destination); //Recursively checks to see if we can forge a path between the start city and the end city
	}

	//Sets up and calls the recursive traversal of routes to find if a destination card has been completed
	private boolean completedDestinationInner(DestinationCard destination){
		ArrayList<RouteNode> nodes = new ArrayList<RouteNode>(); //Sets up the route nodes for traversal
		for(Route r: this.claimedRoutes){
			nodes.add(new RouteNode(r,false));
		}

		String startCity = destination.getStartPoint();
		return this.completedDestinationRecursive(destination, startCity, nodes);
	}

	//Recursively finds a path between two endpoints of a destinaiton card, if possible
	private boolean completedDestinationRecursive(DestinationCard destination, String currentCity, ArrayList<RouteNode> nodes){
		if(currentCity.equals(destination.getEndPoint())){ //Boundary cases
			return true; //Return true if we've reached the endpoint
		}else if(this.allTraversed(nodes)){ //If we haven't reached the endpoint but we've exhausted all of our options
			return false; //Return false
		}

		ArrayList<RouteNode> currentChildren = this.getUntraversedForCity(nodes, currentCity); //Gets a list of unvisited children
		if(currentChildren.size() == 0){ //If we don't have any children (we've reached the end of a branch)
			return false;
		}

		String nextCity;
		for(RouteNode route : currentChildren){ //Iterates through children
			if(route.getRoute().getEndpoints().get(0).equals(currentCity)){ //If we're coming from the start point of the route
				nextCity = route.getRoute().getEndpoints().get(1); //Set the endpoint as the next city
			}else{ //If we're coming from the endpoint of the route
				nextCity = route.getRoute().getEndpoints().get(0); //Set the start point as the next city
			}
			route.setVisited(true); //Set the route as traversed
			if(this.completedDestinationRecursive(destination, nextCity, nodes)){
				return true;
			}
		}

		return false;
	}

	//Checks to see if all of the nodes have been visited
	private boolean allTraversed(ArrayList<RouteNode> nodes){
		boolean existUntraversed = false;
		for(RouteNode node : nodes){
			if(!node.isVisited()){
				existUntraversed = true;
			}
		}
		return existUntraversed;
	}

	//Gets a list of untraversed routes starting at a particular city
	private ArrayList<RouteNode> getUntraversedForCity(ArrayList<RouteNode> nodes, String city){
		ArrayList<RouteNode> returnList = new ArrayList<RouteNode>();
		for(RouteNode node : nodes){ //Iterate through the list of nodes
			if(!node.isVisited() && (node.getRoute().getEndpoints().get(0).equals(city) || node.getRoute().getEndpoints().get(1).equals(city))){ //Adds any unvisited node with the city as a start or enpoint to the list of nodes returned
				returnList.add(node);
			}
		}
		return returnList;
	}

	//Gets the longest length continuous path owned by this player
	public int calculateLongestRoute(){
		//Single-traversal - we only need to hit each node once recursively
		ArrayList<RouteNode> nodes = new ArrayList<RouteNode>();
		for(Route r : this.claimedRoutes){ //Sets up the traversal
			nodes.add(new RouteNode(r,false));
		}

		return this.calculateLongestRouteInner(nodes); //Returns the longest length possible for the routes owned by the user
	}

	//Calculates the longest continuous path
	private int calculateLongestRouteInner(ArrayList<RouteNode> nodes){
		int max = 0;
		int current;
		for(RouteNode node : nodes){
			if(!node.isVisited()){
				current = this.calculateLongestRouteRecursive(node, nodes);
				if(current > max){
					max = current;
				}
			}
		}
		return max;
	}

	//Recursively calculates the longest continuous path from the current node
	private int calculateLongestRouteRecursive(RouteNode currentNode, ArrayList<RouteNode> nodes){
		if(this.allTraversed(nodes)){ //If we haven't reached the endpoint but we've exhausted all of our options
			return currentNode.getRoute().getSpaces(); //Return false
		}

		String startCity = currentNode.getRoute().getEndpoints().get(0);
		String endCity = currentNode.getRoute().getEndpoints().get(1);

		ArrayList<RouteNode> currentChildren = new ArrayList<RouteNode>();
		currentChildren.addAll(this.getUntraversedForCity(nodes, startCity)); //Gets a list of unvisited children going forwards
		currentChildren.addAll(this.getUntraversedForCity(nodes, endCity)); //Gets a list of unvisited children going backwards
		if(currentChildren.size() == 0){ //If we don't have any children (we've reached the end of a branch)
			return currentNode.getRoute().getSpaces();
		}

		String nextCity;
		int max = 0;
		int current;
		for(RouteNode route : currentChildren){ //Iterates through children
			route.setVisited(true); //Set the route as traversed so we don't accidentally traverse it again

			current = this.calculateLongestRouteRecursive(route, nodes); //Gets the longest path of all nodes after it
			if(current > max){
				max = current;
			}
		}
		return currentNode.getRoute().getSpaces() + max; //Returns the longest path after it plus the spaces in this one
	}

	//If this player gets the longest road, call this method on them
	//Updates points
	public void awardLongestRoad(){
		this.points += 10;
		this.longestRoutePoints += 10;
	}
}
