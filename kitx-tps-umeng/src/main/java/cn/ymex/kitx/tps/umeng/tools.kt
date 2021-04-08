package cn.ymex.kitx.tps.umeng

import android.content.Context
import com.ut.device.UTDevice

/**
 * 获取设备id
 *
 * UTDID 是一个 APP 级别的设备标识 ID。
 * 通过设备标识组件，开发者可以实现简单快捷地获取设备 ID，以利于应用程序安全有效地找到特定设备。
 * UTDID 的设计目标是给每一台物理设备提供一个唯一且独立的设备 ID。
 * 在理想状况下，不同的 APP 在同一台设备上可以获取到相同的 UTDID；
 * 同一个App在同一个设备上卸载重装后，可以获取到相同的UTDID；不同的设备的 UTDID 不一样。
 * 但是随着设备变化和隐私权限控制增强，UTDID 在同一台物理设备上可能会发生变化。
 * 因此 UTDID 不提供强一致性的保证，所以不要把 UTDID 应用到有强一致性保证需求的业务中。
 */
fun Context.utdId():String{
    return UTDevice.getUtdid(this)
}