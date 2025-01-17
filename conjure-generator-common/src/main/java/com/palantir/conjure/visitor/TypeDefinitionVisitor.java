/*
 * (c) Copyright 2018 Palantir Technologies Inc. All rights reserved.
 */

package com.palantir.conjure.visitor;

import com.palantir.conjure.spec.AliasDefinition;
import com.palantir.conjure.spec.EnumDefinition;
import com.palantir.conjure.spec.ObjectDefinition;
import com.palantir.conjure.spec.TypeDefinition;
import com.palantir.conjure.spec.TypeName;
import com.palantir.conjure.spec.UnionDefinition;

public final class TypeDefinitionVisitor {

    private TypeDefinitionVisitor() {}

    public static final TypeNameVisitor TYPE_NAME = new TypeNameVisitor();
    public static final AliasDefinitionVisitor ALIAS = new AliasDefinitionVisitor();
    public static final ObjectDefinitionVisitor OBJECT = new ObjectDefinitionVisitor();
    public static final EnumDefinitionVisitor ENUM = new EnumDefinitionVisitor();
    public static final UnionDefinitionVisitor UNION = new UnionDefinitionVisitor();

    public static final IsAliasDefinitionVisitor IS_ALIAS = new IsAliasDefinitionVisitor();
    public static final IsObjectDefinitionVisitor IS_OBJECT = new IsObjectDefinitionVisitor();
    public static final IsEnumDefinitionVisitor IS_ENUM = new IsEnumDefinitionVisitor();
    public static final IsUnionDefinitionVisitor IS_UNION = new IsUnionDefinitionVisitor();

    private static class TypeNameVisitor implements TypeDefinition.Visitor<TypeName> {

        @Override
        public TypeName visitAlias(AliasDefinition value) {
            return value.getTypeName();
        }

        @Override
        public TypeName visitEnum(EnumDefinition value) {
            return value.getTypeName();
        }

        @Override
        public TypeName visitObject(ObjectDefinition value) {
            return value.getTypeName();
        }

        @Override
        public TypeName visitUnion(UnionDefinition value) {
            return value.getTypeName();
        }

        @Override
        public TypeName visitUnknown(String unknownType) {
            throw new IllegalStateException("Unknown definition: " + unknownType);
        }
    }

    private static class AliasDefinitionVisitor extends DefaultDefinitionVisitor<AliasDefinition> {
        @Override
        public AliasDefinition visitAlias(AliasDefinition value) {
            return value;
        }
    }

    private static class ObjectDefinitionVisitor extends DefaultDefinitionVisitor<ObjectDefinition> {
        @Override
        public ObjectDefinition visitObject(ObjectDefinition value) {
            return value;
        }
    }

    private static class EnumDefinitionVisitor extends DefaultDefinitionVisitor<EnumDefinition> {
        @Override
        public EnumDefinition visitEnum(EnumDefinition value) {
            return value;
        }
    }

    private static class UnionDefinitionVisitor extends DefaultDefinitionVisitor<UnionDefinition> {
        @Override
        public UnionDefinition visitUnion(UnionDefinition value) {
            return value;
        }
    }

    private static class DefaultDefinitionVisitor<T> implements TypeDefinition.Visitor<T> {

        @Override
        public T visitAlias(AliasDefinition value) {
            throw new IllegalStateException("Unknown type: " + value);
        }

        @Override
        public T visitEnum(EnumDefinition value) {
            throw new IllegalStateException("Unknown type: " + value);
        }

        @Override
        public T visitObject(ObjectDefinition value) {
            throw new IllegalStateException("Unknown type: " + value);
        }

        @Override
        public T visitUnion(UnionDefinition value) {
            throw new IllegalStateException("Unknown type: " + value);
        }

        @Override
        public T visitUnknown(String unknownType) {
            throw new IllegalStateException("Unknown type: " + unknownType);
        }
    }

    private static class IsAliasDefinitionVisitor extends DefaultIsTypeDefinitionVisitor {
        @Override
        public Boolean visitAlias(AliasDefinition _value) {
            return true;
        }
    }

    private static class IsObjectDefinitionVisitor extends DefaultIsTypeDefinitionVisitor {
        @Override
        public Boolean visitObject(ObjectDefinition _value) {
            return true;
        }
    }

    private static class IsEnumDefinitionVisitor extends DefaultIsTypeDefinitionVisitor {
        @Override
        public Boolean visitEnum(EnumDefinition _value) {
            return true;
        }
    }

    private static class IsUnionDefinitionVisitor extends DefaultIsTypeDefinitionVisitor {
        @Override
        public Boolean visitUnion(UnionDefinition _value) {
            return true;
        }
    }

    private static class DefaultIsTypeDefinitionVisitor implements TypeDefinition.Visitor<Boolean> {
        @Override
        public Boolean visitAlias(AliasDefinition _value) {
            return false;
        }

        @Override
        public Boolean visitEnum(EnumDefinition _value) {
            return false;
        }

        @Override
        public Boolean visitObject(ObjectDefinition _value) {
            return false;
        }

        @Override
        public Boolean visitUnion(UnionDefinition _value) {
            return false;
        }

        @Override
        public Boolean visitUnknown(String _unknownType) {
            return false;
        }
    }
}
