//
//  Created by Petr Chmelar on 23/07/2018.
//  Copyright © 2018 Matee. All rights reserved.
//

public struct NetworkingConstants {
    public static var baseURL: String {
        switch Environment.api {
        case .alpha: "https://devstack-server-production.up.railway.app"
        case .production: "https://devstack-server-production.up.railway.app"
        }
    }
    
    public static var rocketsURL: String {
        switch Environment.api {
        case .alpha: "https://apollo-fullstack-tutorial.herokuapp.com/graphql"
        case .production: "https://apollo-fullstack-tutorial.herokuapp.com/graphql"
        }
    }
    
    public static var googleMapsAPIKey: String {
        switch Environment.api {
        case .alpha: "AIzaSyDj2cQ3ASrD9GCG7O3UIihcovCNihIXjDs"
        case .production: "AIzaSyDj2cQ3ASrD9GCG7O3UIihcovCNihIXjDs"
        }
    }
}
