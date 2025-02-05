//
//  Created by Julia Jakubcova on 02/08/2024
//  Copyright © 2024 Matee. All rights reserved.
//

import Foundation
import KMPShared
import SwiftUI

public extension BaseIntentViewModel {
    func bindState<S: VmState>(stateBinding: Binding<S>) -> () -> Void {
        @Binding var state: S
        _state = stateBinding
        let coroutineJob = subscribeToState { data in
            let value = data as! S // swiftlint:disable:this force_cast
            state = value
        } onComplete: {
            // do nothing
        } onThrow: { _ in
            // do nothing
        }
        return { coroutineJob.cancel(cause: nil) }
    }
}

public extension BaseIntentViewModel {
    func asyncStreamFromEvents<E: VmEvent>() -> AsyncStream<E> {
        return AsyncStream<E> { continuation in
            let coroutineJob = subscribeToEvents { data in
                let value = data as! E // swiftlint:disable:this force_cast
                continuation.yield(value)
            } onComplete: {
                continuation.finish()
            } onThrow: { _ in
                continuation.finish()
            }
            continuation.onTermination = { _ in
                coroutineJob.cancel(cause: nil)
            }
        }
    }
}
