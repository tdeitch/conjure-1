/*
 * (c) Copyright 2018 Palantir Technologies Inc. All rights reserved.
 */

package com.palantir.conjure.defs.types;

import com.google.common.base.Preconditions;
import com.palantir.conjure.defs.ConjureParserUtils;
import com.palantir.conjure.defs.types.names.ConjurePackage;
import com.palantir.conjure.defs.types.names.TypeName;
import com.palantir.conjure.defs.types.reference.ExternalType;
import com.palantir.conjure.parser.types.BaseObjectTypeDefinition;
import com.palantir.conjure.parser.types.ConjureTypeVisitor;
import com.palantir.conjure.parser.types.TypesDefinition;
import com.palantir.conjure.parser.types.builtin.AnyType;
import com.palantir.conjure.parser.types.builtin.BinaryType;
import com.palantir.conjure.parser.types.builtin.DateTimeType;
import com.palantir.conjure.parser.types.collect.ListType;
import com.palantir.conjure.parser.types.collect.MapType;
import com.palantir.conjure.parser.types.collect.OptionalType;
import com.palantir.conjure.parser.types.collect.SetType;
import com.palantir.conjure.parser.types.primitive.PrimitiveType;
import com.palantir.conjure.parser.types.reference.ConjureImports;
import com.palantir.conjure.parser.types.reference.ExternalTypeDefinition;
import com.palantir.conjure.parser.types.reference.ForeignReferenceType;
import com.palantir.conjure.parser.types.reference.LocalReferenceType;
import java.util.Optional;

/** The core translator between parsed/raw types and the "defs" representation exposed to compilers. */
public final class ConjureTypeParserVisitor implements ConjureTypeVisitor<Type> {

    public interface ReferenceTypeResolver {
        Type resolve(LocalReferenceType reference);
        Type resolve(ForeignReferenceType reference);
    }

    // TODO(rfink): Add explicit test coverage
    public static final class ByParsedRepresentationTypeNameResolver implements ReferenceTypeResolver {

        private final TypesDefinition types;

        public ByParsedRepresentationTypeNameResolver(TypesDefinition types) {
            this.types = types;
        }

        @Override
        public Type resolve(LocalReferenceType reference) {
            return resolveFromTypeName(reference.type(), types);
        }

        @Override
        public Type resolve(ForeignReferenceType reference) {
            ConjureImports conjureImports = types.conjureImports().get(reference.namespace());
            Preconditions.checkNotNull(conjureImports, "Import not found for namespace: %s", reference.namespace());
            return resolveFromTypeName(reference.type(), conjureImports.conjure().types());
        }

        private static Type resolveFromTypeName(
                com.palantir.conjure.parser.types.names.TypeName name, TypesDefinition types) {
            Optional<ConjurePackage> defaultPackage =
                    types.definitions().defaultConjurePackage().map(ConjureParserUtils::parseConjurePackage);
            BaseObjectTypeDefinition maybeDirectDef = types.definitions().objects().get(name);
            ConjurePackage conjurePackage;
            String typeName;
            if (maybeDirectDef == null) {
                ExternalTypeDefinition maybeExternalDef = types.imports().get(name);
                if (maybeExternalDef == null) {
                    throw new IllegalStateException("Unknown LocalReferenceType: " + name);
                }

                // External import
                Optional<String> externalPath = maybeExternalDef.external().values().stream().findFirst();

                if (!externalPath.isPresent()) {
                    throw new IllegalStateException("Unknown export type: " + name);
                }

                int lastIndex = externalPath.get().lastIndexOf(".");
                conjurePackage = ConjurePackage.of(externalPath.get().substring(0, lastIndex));
                typeName = externalPath.get().substring(lastIndex + 1);

                return ExternalType.builder()
                        .externalReference(TypeName.of(typeName, conjurePackage))
                        .fallback(ConjureParserUtils.parsePrimitiveType(maybeExternalDef.baseType()))
                        .build();
            } else {
                // Conjure-defined object
                conjurePackage = ConjureParserUtils.parsePackageOrElseThrow(
                        maybeDirectDef.conjurePackage(), defaultPackage);
                return com.palantir.conjure.defs.types.reference.LocalReferenceType.of(
                        TypeName.of(name.name(), conjurePackage));
            }
        }
    }

    private final ReferenceTypeResolver nameResolver;

    public ConjureTypeParserVisitor(ReferenceTypeResolver nameResolver) {
        this.nameResolver = nameResolver;
    }

    @Override
    public Type visitAny(AnyType type) {
        return com.palantir.conjure.defs.types.builtin.AnyType.of();
    }

    @Override
    public Type visitList(ListType type) {
        return com.palantir.conjure.defs.types.collect.ListType.of(type.itemType().visit(this));
    }

    @Override
    public Type visitMap(MapType type) {
        return com.palantir.conjure.defs.types.collect.MapType.of(
                type.keyType().visit(this), type.valueType().visit(this));
    }

    @Override
    public Type visitOptional(OptionalType type) {
        return com.palantir.conjure.defs.types.collect.OptionalType.of(type.itemType().visit(this));
    }

    @Override
    public Type visitPrimitive(PrimitiveType type) {
        return ConjureParserUtils.parsePrimitiveType(type);
    }

    @Override
    public Type visitLocalReference(LocalReferenceType type) {
        return nameResolver.resolve(type);
    }

    @Override
    public Type visitForeignReference(ForeignReferenceType type) {
        return nameResolver.resolve(type);
    }

    @Override
    public Type visitSet(SetType type) {
        return com.palantir.conjure.defs.types.collect.SetType.of(type.itemType().visit(this));
    }

    @Override
    public Type visitBinary(BinaryType type) {
        return com.palantir.conjure.defs.types.builtin.BinaryType.of();
    }

    @Override
    public Type visitDateTime(DateTimeType type) {
        return com.palantir.conjure.defs.types.builtin.DateTimeType.of();
    }
}