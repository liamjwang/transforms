package org.liamwang.transforms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.liamwang.transforms.threed.Transform3D;


public class Transform3DTest {

    static Stream<Arguments> equalsProvider() {
        return Stream.of(
            arguments(new Transform3D(1, 2, 3, 1, 3, 4), new Transform3D(1, 2, 3, 1, 3, 4)),
            arguments(new Transform3D(46, -22, 0, 5, 999, 3), new Transform3D(46, -22, 0, 5, 999 + Math.PI * 2, 3 - Math.PI * 2)),
            arguments(new Transform3D(0, 2, 3, 0, 3, 4), new Transform3D(0, 2, 3, -Math.PI * 2, 3, 4))
        );
    }

    @ParameterizedTest(name = "{0} = {1}")
    @MethodSource("equalsProvider")
    public void testEquals(Transform3D a, Transform3D b) {
        assertEquals(a, b);
    }

    static Stream<Arguments> notEqualsProvider() {
        return Stream.of(
            arguments(new Transform3D(1, 2, 3, 1, 3, 4), new Transform3D(1.01, 2, 3, 1, 3, 4)),
            arguments(new Transform3D(46, -22, 0, 5, 999, 3), new Transform3D(46, -22, 0, 5, 999 + Math.PI * 2, 3.1 - Math.PI * 2)),
            arguments(new Transform3D(0, 2, 3, 0, 3, 4), new Transform3D(0, 2.0001, 3, -Math.PI * 2, 3, 4.00001))
        );
    }

    @ParameterizedTest(name = "{0} = {1}")
    @MethodSource("notEqualsProvider")
    public void testNotEquals(Transform3D a, Transform3D b) {
        assertNotEquals(a, b);
    }

    static Stream<Arguments> addSubtractProvider() {
        return Stream.of(
            arguments(new Transform3D(1, 1, 1, 0, 0, Math.PI), new Transform3D(1, 1, -1, 0, 0, Math.PI), Transform3D.IDENTITY),
            arguments(new Transform3D(46, -22, 0, 0, Math.PI, Math.PI), new Transform3D(46, -22, 0, Math.PI / 2, 0, 0), new Transform3D(92, 0, 0, -Math.PI / 2, 0, 0)), // iffy
            arguments(new Transform3D(2, 2, 3, 0, 3, 4), new Transform3D(0, 2.0001, 3, -Math.PI * 2, 3, 4.00001),
                new Transform3D(0.9248275178, 0.6926473939, -0.1835881183, 0.1071309642278483, -0.04840863197943932, 0.01017290170723382)) // iffy
        );
    }

    @ParameterizedTest(name = "{0} + {1} = {2}")
    @MethodSource("addSubtractProvider")
    public void testAdd(Transform3D a, Transform3D b, Transform3D sum) {
        assertEquals(sum, a.add(b));
    }

    @ParameterizedTest(name = "{2} - {1} = {0}")
    @MethodSource("addSubtractProvider")
    public void testSubtract(Transform3D a, Transform3D b, Transform3D sum) {
        assertEquals(a, sum.subtract(b));
    }

    static Stream<Arguments> negateProvider() {
        return Stream.of(
            arguments(new Transform3D(1, 0, 0, 0, 0, Math.PI), new Transform3D(1, 0, 0, 0, 0, -Math.PI), Transform3D.IDENTITY),
            arguments(new Transform3D(46, -22, 0, 0, Math.PI, Math.PI), new Transform3D(-46, -22, 0, -Math.PI, 0, 0)) // iffy
        );
    }

    @ParameterizedTest(name = "{0} = - {1}")
    @MethodSource("negateProvider")
    public void testNegate(Transform3D a, Transform3D b) {
        assertEquals(b, a.negate());
    }
}
