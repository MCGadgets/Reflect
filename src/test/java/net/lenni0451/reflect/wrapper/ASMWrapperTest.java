package net.lenni0451.reflect.wrapper;

import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static net.lenni0451.reflect.wrapper.ASMWrapper.opcode;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ASMWrapperTest {

    @Test
    void test() {
        ASMWrapper w = ASMWrapper.create(opcode("ACC_PUBLIC"), "net/lenni0451/reflect/wrapper/ASMWrapperTestSupplier", null, "java/lang/Object", new String[]{"java/util/function/Supplier"});

        ASMWrapper.MethodVisitorAccess c = w.visitMethod(opcode("ACC_PUBLIC"), "<init>", "()V", null, null);
        c.visitVarInsn(opcode("ALOAD"), 0);
        c.visitMethodInsn(opcode("INVOKESPECIAL"), "java/lang/Object", "<init>", "()V", false);
        c.visitInsn(opcode("RETURN"));
        c.visitMaxs(1, 1);
        c.visitEnd();

        ASMWrapper.MethodVisitorAccess g = w.visitMethod(opcode("ACC_PUBLIC"), "get", "()Ljava/lang/Object;", null, null);
        g.visitLdcInsn("Hello World");
        g.visitInsn(opcode("ARETURN"));
        g.visitMaxs(1, 1);
        g.visitEnd();

        Class<?> clazz = w.defineAnonymously(ASMWrapperTest.class);
        Supplier<String> supplier = (Supplier<String>) assertDoesNotThrow(() -> clazz.getDeclaredConstructor().newInstance());
        assertEquals("Hello World", supplier.get());
    }

}
