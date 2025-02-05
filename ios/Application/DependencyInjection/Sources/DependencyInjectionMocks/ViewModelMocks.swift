//
//  Created by Julia Jakubcova on 02/08/2024
//  Copyright © 2024 Matee. All rights reserved.
//

#if DEBUG
import CoreLocation
import DependencyInjection
import Factory
import KMPShared
@testable import SharedDomain
import SharedDomainMocks

public extension Container {
    func registerViewModelMocks() {
        
        // Sample
        sampleSharedViewModel.register {
            SampleSharedViewModel(getSampleText: self.getSampleTextUseCase())
        }
    }
}
#endif
