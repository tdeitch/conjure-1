import { IHttpApiBridge } from "@foundry/conjure-fe-lib";
import { ICreateDatasetRequest } from "@palantir/product";
import { IBackingFileSystem } from "@palantir/product-datasets";
import { IDataset } from "@palantir/product-datasets";

export class TestService {
    private bridge: IHttpApiBridge;

    constructor(
        bridge: IHttpApiBridge
    ) {
        this.bridge = bridge;
    }

    public createDataset(
        request: ICreateDatasetRequest,
        testHeaderArg: string
    ): Promise<IDataset> {
        return this.bridge.callEndpoint<IDataset>({
            data: request,
            endpointName: "createDataset",
            endpointPath: "/catalog/datasets",
            headers: {
                "Test-Header": testHeaderArg,
            },
            method: "POST",
            pathArguments: [
            ],
            queryArguments: {
            },
            requestMediaType: "application/json",
            requiredHeaders: [
                "Authorization",
                "Test-Header",
            ],
            responseMediaType: "application/json",
        });
    }

    public getBranches(
        datasetRid: string
    ): Promise<string[]> {
        return this.bridge.callEndpoint<string[]>({
            data: undefined,
            endpointName: "getBranches",
            endpointPath: "/catalog/datasets/{datasetRid}/branches",
            headers: {
            },
            method: "GET",
            pathArguments: [
                datasetRid,
            ],
            queryArguments: {
            },
            requestMediaType: "application/json",
            requiredHeaders: [
                "Authorization",
            ],
            responseMediaType: "application/json",
        });
    }

    public getBranchesDeprecated(
        datasetRid: string
    ): Promise<string[]> {
        return this.bridge.callEndpoint<string[]>({
            data: undefined,
            endpointName: "getBranchesDeprecated",
            endpointPath: "/catalog/datasets/{datasetRid}/branchesDeprecated",
            headers: {
            },
            method: "GET",
            pathArguments: [
                datasetRid,
            ],
            queryArguments: {
            },
            requestMediaType: "application/json",
            requiredHeaders: [
                "Authorization",
            ],
            responseMediaType: "application/json",
        });
    }

    public getDataset(
        datasetRid: string
    ): Promise<IDataset | null | undefined> {
        return this.bridge.callEndpoint<IDataset | null | undefined>({
            data: undefined,
            endpointName: "getDataset",
            endpointPath: "/catalog/datasets/{datasetRid}",
            headers: {
            },
            method: "GET",
            pathArguments: [
                datasetRid,
            ],
            queryArguments: {
            },
            requestMediaType: "application/json",
            requiredHeaders: [
                "Authorization",
            ],
            responseMediaType: "application/json",
        });
    }

    public getFileSystems(): Promise<{ [key: string]: IBackingFileSystem }> {
        return this.bridge.callEndpoint<{ [key: string]: IBackingFileSystem }>({
            data: undefined,
            endpointName: "getFileSystems",
            endpointPath: "/catalog/fileSystems",
            headers: {
            },
            method: "GET",
            pathArguments: [
            ],
            queryArguments: {
            },
            requestMediaType: "application/json",
            requiredHeaders: [
                "Authorization",
            ],
            responseMediaType: "application/json",
        });
    }

    public getRawData(
        datasetRid: string
    ): Promise<any> {
        return this.bridge.callEndpoint<any>({
            data: undefined,
            endpointName: "getRawData",
            endpointPath: "/catalog/datasets/{datasetRid}/raw",
            headers: {
            },
            method: "GET",
            pathArguments: [
                datasetRid,
            ],
            queryArguments: {
            },
            requestMediaType: "application/json",
            requiredHeaders: [
                "Authorization",
            ],
            responseMediaType: "application/octet-stream",
        });
    }

    public maybeGetRawData(
        datasetRid: string
    ): Promise<any | null | undefined> {
        return this.bridge.callEndpoint<any | null | undefined>({
            data: undefined,
            endpointName: "maybeGetRawData",
            endpointPath: "/catalog/datasets/{datasetRid}/raw-maybe",
            headers: {
            },
            method: "GET",
            pathArguments: [
                datasetRid,
            ],
            queryArguments: {
            },
            requestMediaType: "application/json",
            requiredHeaders: [
                "Authorization",
            ],
            responseMediaType: "application/json",
        });
    }

    public resolveBranch(
        datasetRid: string,
        branch: string
    ): Promise<string | null | undefined> {
        return this.bridge.callEndpoint<string | null | undefined>({
            data: undefined,
            endpointName: "resolveBranch",
            endpointPath: "/catalog/datasets/{datasetRid}/branches/{branch:.+}/resolve",
            headers: {
            },
            method: "GET",
            pathArguments: [
                datasetRid,
                branch,
            ],
            queryArguments: {
            },
            requestMediaType: "application/json",
            requiredHeaders: [
                "Authorization",
            ],
            responseMediaType: "application/json",
        });
    }

    public testBoolean(): Promise<boolean> {
        return this.bridge.callEndpoint<boolean>({
            data: undefined,
            endpointName: "testBoolean",
            endpointPath: "/catalog/boolean",
            headers: {
            },
            method: "GET",
            pathArguments: [
            ],
            queryArguments: {
            },
            requestMediaType: "application/json",
            requiredHeaders: [
                "Authorization",
            ],
            responseMediaType: "application/json",
        });
    }

    public testDouble(): Promise<number | "NaN"> {
        return this.bridge.callEndpoint<number | "NaN">({
            data: undefined,
            endpointName: "testDouble",
            endpointPath: "/catalog/double",
            headers: {
            },
            method: "GET",
            pathArguments: [
            ],
            queryArguments: {
            },
            requestMediaType: "application/json",
            requiredHeaders: [
                "Authorization",
            ],
            responseMediaType: "application/json",
        });
    }

    public testInteger(): Promise<number> {
        return this.bridge.callEndpoint<number>({
            data: undefined,
            endpointName: "testInteger",
            endpointPath: "/catalog/integer",
            headers: {
            },
            method: "GET",
            pathArguments: [
            ],
            queryArguments: {
            },
            requestMediaType: "application/json",
            requiredHeaders: [
                "Authorization",
            ],
            responseMediaType: "application/json",
        });
    }

    public testParam(
        datasetRid: string
    ): Promise<string | null | undefined> {
        return this.bridge.callEndpoint<string | null | undefined>({
            data: undefined,
            endpointName: "testParam",
            endpointPath: "/catalog/datasets/{datasetRid}/testParam",
            headers: {
            },
            method: "GET",
            pathArguments: [
                datasetRid,
            ],
            queryArguments: {
            },
            requestMediaType: "application/json",
            requiredHeaders: [
                "Authorization",
            ],
            responseMediaType: "application/json",
        });
    }

    public testQueryParams(
        something: string,
        implicit: string
    ): Promise<number> {
        return this.bridge.callEndpoint<number>({
            data: undefined,
            endpointName: "testQueryParams",
            endpointPath: "/catalog/test-query-params",
            headers: {
            },
            method: "GET",
            pathArguments: [
            ],
            queryArguments: {
                different: something,
                implicit: implicit,
            },
            requestMediaType: "application/json",
            requiredHeaders: [
                "Authorization",
            ],
            responseMediaType: "application/json",
        });
    }

    public uploadRawData(
        input: any
    ): Promise<void> {
        return this.bridge.callEndpoint<void>({
            data: input,
            endpointName: "uploadRawData",
            endpointPath: "/catalog/datasets/upload-raw",
            headers: {
            },
            method: "POST",
            pathArguments: [
            ],
            queryArguments: {
            },
            requestMediaType: "application/octet-stream",
            requiredHeaders: [
                "Authorization",
            ],
            responseMediaType: "application/json",
        });
    }
}
