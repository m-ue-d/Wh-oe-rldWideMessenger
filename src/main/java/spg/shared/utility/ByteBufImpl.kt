package spg.shared.utility

import io.netty.buffer.ByteBuf
import io.netty.buffer.ByteBufAllocator
import io.netty.buffer.ByteBufProcessor
import java.io.InputStream
import java.io.OutputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.GatheringByteChannel
import java.nio.channels.ScatteringByteChannel
import java.nio.charset.Charset


open class ByteBufImpl(private val parent: ByteBuf) : ByteBuf() {
	override fun capacity() : Int {
		return parent.capacity()
	}

	override fun capacity(capacity : Int) : ByteBuf {
		return parent.capacity(capacity)
	}

	override fun maxCapacity() : Int {
		return parent.maxCapacity()
	}

	override fun alloc() : ByteBufAllocator {
		return parent.alloc()
	}

	override fun order() : ByteOrder {
		return parent.order()
	}

	override fun order(byteOrder : ByteOrder) : ByteBuf {
		return parent.order(byteOrder)
	}

	override fun unwrap() : ByteBuf {
		return parent.unwrap()
	}

	override fun isDirect() : Boolean {
		return parent.isDirect
	}

	override fun readerIndex() : Int {
		return parent.readerIndex()
	}

	override fun readerIndex(index : Int) : ByteBuf {
		return parent.readerIndex(index)
	}

	override fun writerIndex() : Int {
		return parent.writerIndex()
	}

	override fun writerIndex(index : Int) : ByteBuf {
		return parent.writerIndex(index)
	}

	override fun setIndex(readerIndex : Int, writerIndex : Int) : ByteBuf {
		return parent.setIndex(readerIndex, writerIndex)
	}

	override fun readableBytes() : Int {
		return parent.readableBytes()
	}

	override fun writableBytes() : Int {
		return parent.writableBytes()
	}

	override fun maxWritableBytes() : Int {
		return parent.maxWritableBytes()
	}

	override fun isReadable() : Boolean {
		return parent.isReadable
	}

	override fun isReadable(size : Int) : Boolean {
		return parent.isReadable(size)
	}

	override fun isWritable() : Boolean {
		return parent.isWritable
	}

	override fun isWritable(size : Int) : Boolean {
		return parent.isWritable(size)
	}

	override fun clear() : ByteBuf {
		return parent.clear()
	}

	override fun markReaderIndex() : ByteBuf {
		return parent.markReaderIndex()
	}

	override fun resetReaderIndex() : ByteBuf {
		return parent.resetReaderIndex()
	}

	override fun markWriterIndex() : ByteBuf {
		return parent.markWriterIndex()
	}

	override fun resetWriterIndex() : ByteBuf {
		return parent.resetWriterIndex()
	}

	override fun discardReadBytes() : ByteBuf {
		return parent.discardReadBytes()
	}

	override fun discardSomeReadBytes() : ByteBuf {
		return parent.discardSomeReadBytes()
	}

	override fun ensureWritable(minBytes : Int) : ByteBuf {
		return parent.ensureWritable(minBytes)
	}

	override fun ensureWritable(minBytes : Int, force : Boolean) : Int {
		return parent.ensureWritable(minBytes, force)
	}

	override fun getBoolean(index : Int) : Boolean {
		return parent.getBoolean(index)
	}

	override fun getByte(index : Int) : Byte {
		return parent.getByte(index)
	}

	override fun getUnsignedByte(index : Int) : Short {
		return parent.getUnsignedByte(index)
	}

	override fun getShort(index : Int) : Short {
		return parent.getShort(index)
	}

	override fun getUnsignedShort(index : Int) : Int {
		return parent.getUnsignedShort(index)
	}

	override fun getMedium(index : Int) : Int {
		return parent.getMedium(index)
	}

	override fun getUnsignedMedium(index : Int) : Int {
		return parent.getUnsignedMedium(index)
	}

	override fun getInt(index : Int) : Int {
		return parent.getInt(index)
	}

	override fun getUnsignedInt(index : Int) : Long {
		return parent.getUnsignedInt(index)
	}

	override fun getLong(index : Int) : Long {
		return parent.getLong(index)
	}

	override fun getChar(index : Int) : Char {
		return parent.getChar(index)
	}

	override fun getFloat(index : Int) : Float {
		return parent.getFloat(index)
	}

	override fun getDouble(index : Int) : Double {
		return parent.getDouble(index)
	}

	override fun getBytes(index : Int, buf : ByteBuf) : ByteBuf {
		return parent.getBytes(index, buf)
	}

	override fun getBytes(index : Int, buf : ByteBuf, length : Int) : ByteBuf {
		return parent.getBytes(index, buf, length)
	}

	override fun getBytes(index : Int, buf : ByteBuf, outputIndex : Int, length : Int) : ByteBuf {
		return parent.getBytes(index, buf, outputIndex, length)
	}

	override fun getBytes(index : Int, bytes : ByteArray) : ByteBuf {
		return parent.getBytes(index, bytes)
	}

	override fun getBytes(index : Int, bytes : ByteArray, outputIndex : Int, length : Int) : ByteBuf {
		return parent.getBytes(index, bytes, outputIndex, length)
	}

	override fun getBytes(index : Int, buf : ByteBuffer) : ByteBuf {
		return parent.getBytes(index, buf)
	}

	override fun getBytes(index : Int, stream : OutputStream, length : Int) : ByteBuf {
		return parent.getBytes(index, stream, length)
	}

	override fun getBytes(index : Int, channel : GatheringByteChannel, length : Int) : Int {
		return parent.getBytes(index, channel, length)
	}

	override fun setBoolean(index : Int, value : Boolean) : ByteBuf {
		return parent.setBoolean(index, value)
	}

	override fun setByte(index : Int, value : Int) : ByteBuf {
		return parent.setByte(index, value)
	}

	override fun setShort(index : Int, value : Int) : ByteBuf {
		return parent.setShort(index, value)
	}

	override fun setMedium(index : Int, value : Int) : ByteBuf {
		return parent.setMedium(index, value)
	}

	override fun setInt(index : Int, value : Int) : ByteBuf {
		return parent.setInt(index, value)
	}

	override fun setLong(index : Int, value : Long) : ByteBuf {
		return parent.setLong(index, value)
	}

	override fun setChar(index : Int, value : Int) : ByteBuf {
		return parent.setChar(index, value)
	}

	override fun setFloat(index : Int, value : Float) : ByteBuf {
		return parent.setFloat(index, value)
	}

	override fun setDouble(index : Int, value : Double) : ByteBuf {
		return parent.setDouble(index, value)
	}

	override fun setBytes(index : Int, buf : ByteBuf) : ByteBuf {
		return parent.setBytes(index, buf)
	}

	override fun setBytes(index : Int, buf : ByteBuf, length : Int) : ByteBuf {
		return parent.setBytes(index, buf, length)
	}

	override fun setBytes(index : Int, buf : ByteBuf, sourceIndex : Int, length : Int) : ByteBuf {
		return parent.setBytes(index, buf, sourceIndex, length)
	}

	override fun setBytes(index : Int, bytes : ByteArray) : ByteBuf {
		return parent.setBytes(index, bytes)
	}

	override fun setBytes(index : Int, bytes : ByteArray, sourceIndex : Int, length : Int) : ByteBuf {
		return parent.setBytes(index, bytes, sourceIndex, length)
	}

	override fun setBytes(index : Int, buf : ByteBuffer) : ByteBuf {
		return parent.setBytes(index, buf)
	}

	override fun setBytes(index : Int, stream : InputStream, length : Int) : Int {
		return parent.setBytes(index, stream, length)
	}

	override fun setBytes(index : Int, channel : ScatteringByteChannel, length : Int) : Int {
		return parent.setBytes(index, channel, length)
	}

	override fun setZero(index : Int, length : Int) : ByteBuf {
		return parent.setZero(index, length)
	}

	override fun readBoolean() : Boolean {
		return parent.readBoolean()
	}

	override fun readByte() : Byte {
		return parent.readByte()
	}

	override fun readUnsignedByte() : Short {
		return parent.readUnsignedByte()
	}

	override fun readShort() : Short {
		return parent.readShort()
	}

	override fun readUnsignedShort() : Int {
		return parent.readUnsignedShort()
	}

	override fun readMedium() : Int {
		return parent.readMedium()
	}

	override fun readUnsignedMedium() : Int {
		return parent.readUnsignedMedium()
	}

	override fun readInt() : Int {
		return parent.readInt()
	}

	override fun readUnsignedInt() : Long {
		return parent.readUnsignedInt()
	}

	override fun readLong() : Long {
		return parent.readLong()
	}

	override fun readChar() : Char {
		return parent.readChar()
	}

	override fun readFloat() : Float {
		return parent.readFloat()
	}

	override fun readDouble() : Double {
		return parent.readDouble()
	}

	override fun readBytes(length : Int) : ByteBuf {
		return parent.readBytes(length)
	}

	override fun readSlice(length : Int) : ByteBuf {
		return parent.readSlice(length)
	}

	override fun readBytes(buf : ByteBuf) : ByteBuf {
		return parent.readBytes(buf)
	}

	override fun readBytes(buf : ByteBuf, length : Int) : ByteBuf {
		return parent.readBytes(buf, length)
	}

	override fun readBytes(buf : ByteBuf, outputIndex : Int, length : Int) : ByteBuf {
		return parent.readBytes(buf, outputIndex, length)
	}

	override fun readBytes(bytes : ByteArray) : ByteBuf {
		return parent.readBytes(bytes)
	}

	override fun readBytes(bytes : ByteArray, outputIndex : Int, length : Int) : ByteBuf {
		return parent.readBytes(bytes, outputIndex, length)
	}

	override fun readBytes(buf : ByteBuffer) : ByteBuf {
		return parent.readBytes(buf)
	}

	override fun readBytes(stream : OutputStream, length : Int) : ByteBuf {
		return parent.readBytes(stream, length)
	}

	override fun readBytes(channel : GatheringByteChannel, length : Int) : Int {
		return parent.readBytes(channel, length)
	}

	override fun skipBytes(length : Int) : ByteBuf {
		return parent.skipBytes(length)
	}

	override fun writeBoolean(value : Boolean) : ByteBuf {
		return parent.writeBoolean(value)
	}

	override fun writeByte(value : Int) : ByteBuf {
		return parent.writeByte(value)
	}

	override fun writeShort(value : Int) : ByteBuf {
		return parent.writeShort(value)
	}

	override fun writeMedium(value : Int) : ByteBuf {
		return parent.writeMedium(value)
	}

	override fun writeInt(value : Int) : ByteBuf {
		return parent.writeInt(value)
	}

	override fun writeLong(value : Long) : ByteBuf {
		return parent.writeLong(value)
	}

	override fun writeChar(value : Int) : ByteBuf {
		return parent.writeChar(value)
	}

	override fun writeFloat(value : Float) : ByteBuf {
		return parent.writeFloat(value)
	}

	override fun writeDouble(value : Double) : ByteBuf {
		return parent.writeDouble(value)
	}

	override fun writeBytes(buf : ByteBuf) : ByteBuf {
		return parent.writeBytes(buf)
	}

	override fun writeBytes(buf : ByteBuf, length : Int) : ByteBuf {
		return parent.writeBytes(buf, length)
	}

	override fun writeBytes(buf : ByteBuf, sourceIndex : Int, length : Int) : ByteBuf {
		return parent.writeBytes(buf, sourceIndex, length)
	}

	override fun writeBytes(bytes : ByteArray) : ByteBuf {
		return parent.writeBytes(bytes)
	}

	override fun writeBytes(bytes : ByteArray, sourceIndex : Int, length : Int) : ByteBuf {
		return parent.writeBytes(bytes, sourceIndex, length)
	}

	override fun writeBytes(buf : ByteBuffer) : ByteBuf {
		return parent.writeBytes(buf)
	}

	override fun writeBytes(stream : InputStream, length : Int) : Int {
		return parent.writeBytes(stream, length)
	}

	override fun writeBytes(channel : ScatteringByteChannel, length : Int) : Int {
		return parent.writeBytes(channel, length)
	}

	override fun writeZero(length : Int) : ByteBuf {
		return parent.writeZero(length)
	}

	override fun indexOf(from : Int, to : Int, value : Byte) : Int {
		return parent.indexOf(from, to, value)
	}

	override fun bytesBefore(value : Byte) : Int {
		return parent.bytesBefore(value)
	}

	override fun bytesBefore(length : Int, value : Byte) : Int {
		return parent.bytesBefore(length, value)
	}

	override fun bytesBefore(index : Int, length : Int, value : Byte) : Int {
		return parent.bytesBefore(index, length, value)
	}

	override fun forEachByte(processor : ByteBufProcessor?) : Int {
		return parent.forEachByte(processor)
	}

	override fun forEachByte(index : Int, length : Int, processor : ByteBufProcessor?) : Int {
		return parent.forEachByte(index, length, processor)
	}

	override fun forEachByteDesc(processor : ByteBufProcessor?) : Int {
		return parent.forEachByteDesc(processor)
	}

	override fun forEachByteDesc(index : Int, length : Int, processor : ByteBufProcessor?) : Int {
		return parent.forEachByteDesc(index, length, processor)
	}

	override fun copy() : ByteBuf {
		return parent.copy()
	}

	override fun copy(index : Int, length : Int) : ByteBuf {
		return parent.copy(index, length)
	}

	override fun slice() : ByteBuf {
		return parent.slice()
	}

	override fun slice(index : Int, length : Int) : ByteBuf {
		return parent.slice(index, length)
	}

	override fun duplicate() : ByteBuf {
		return parent.duplicate()
	}

	override fun nioBufferCount() : Int {
		return parent.nioBufferCount()
	}

	override fun nioBuffer() : ByteBuffer {
		return parent.nioBuffer()
	}

	override fun nioBuffer(index : Int, length : Int) : ByteBuffer {
		return parent.nioBuffer(index, length)
	}

	override fun internalNioBuffer(index : Int, length : Int) : ByteBuffer {
		return parent.internalNioBuffer(index, length)
	}

	override fun nioBuffers() : Array<ByteBuffer> {
		return parent.nioBuffers()
	}

	override fun nioBuffers(index : Int, length : Int) : Array<ByteBuffer> {
		return parent.nioBuffers(index, length)
	}

	override fun hasArray() : Boolean {
		return parent.hasArray()
	}

	override fun array() : ByteArray {
		return parent.array()
	}

	override fun arrayOffset() : Int {
		return parent.arrayOffset()
	}

	override fun hasMemoryAddress() : Boolean {
		return parent.hasMemoryAddress()
	}

	override fun memoryAddress() : Long {
		return parent.memoryAddress()
	}

	override fun toString(charset : Charset) : String {
		return parent.toString(charset)
	}

	override fun toString(index : Int, length : Int, charset : Charset) : String {
		return parent.toString(index, length, charset)
	}

	override fun equals(other : Any?) : Boolean {
		return parent == other
	}

	override fun hashCode() : Int {
		return parent.hashCode()
	}

	override fun compareTo(other : ByteBuf) : Int {
		return parent.compareTo(other)
	}

	override fun toString() : String {
		return parent.toString()
	}

	override fun retain() : ByteBuf {
		return parent.retain()
	}

	override fun touch() : ByteBuf {
		return parent.touch()
	}

	override fun touch(hint : Any?) : ByteBuf {
		return parent.touch(hint)
	}

	override fun refCnt() : Int {
		return parent.refCnt()
	}

	override fun retain(increment : Int) : ByteBuf {
		return parent.retain(increment)
	}

	override fun release() : Boolean {
		return parent.release()
	}

	override fun release(decrement : Int) : Boolean {
		return parent.release(decrement)
	}
}